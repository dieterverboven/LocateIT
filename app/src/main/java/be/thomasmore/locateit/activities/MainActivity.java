package be.thomasmore.locateit.activities;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arubanetworks.meridian.editor.Placemark;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;
import com.arubanetworks.meridian.search.SearchFragment;

import java.io.Console;
import java.util.Date;
import java.util.logging.ConsoleHandler;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.Application;
import be.thomasmore.locateit.classes.Feedback;
import be.thomasmore.locateit.classes.UserSettings;
import be.thomasmore.locateit.helpers.JsonHelper;
import be.thomasmore.locateit.http.HttpWriter;
import be.thomasmore.locateit.services.LocalStorage;

import com.android.volley.VolleyError;
import com.arubanetworks.meridian.campaigns.CampaignsService;
import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.internal.util.Strings;
import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;
import com.arubanetworks.meridian.requests.MeridianRequest;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    UserSettings userSettingsLocal;

    private String afdeling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            afdeling = bundle.getString("afdeling");
        }

        if (savedInstanceState == null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            else
                selectItem(0);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userSettingsLocal = LocalStorage.getUserSettingsLocal(getApplicationContext());

        if (!userSettingsLocal.isAllowPrivacy()) {
            showPrivacyDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void showFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.dialog_feedback, null);

        builder.setTitle(R.string.dialog_feedback)
                .setView(viewInflater)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Date date = new Date();

                        EditText beschrijving = (EditText) viewInflater.findViewById(R.id.beschrijving);
                        Spinner typeSpinner = (Spinner) viewInflater.findViewById(R.id.scoreSpinner);

                        int selectedItem = typeSpinner.getSelectedItemPosition() + 1;
                        String text = beschrijving.getText().toString();

                        Feedback feedback = new Feedback();

                        feedback.setTimestamp(date.getTime());
                        feedback.setScore(selectedItem);
                        feedback.setBeschrijving(text);

                        //POST request naar backend
                        JsonHelper jsonHelper = new JsonHelper();
                        HttpWriter httpWriter = new HttpWriter();
                        httpWriter.setJsonObject(jsonHelper.getJsonFeedback(feedback));

                        httpWriter.execute("https://locateit-backend.herokuapp.com/feedback");
                        giveFeedback();
                    }
                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Accept privacy before you can use app
    private void showPrivacyDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.dialog_privacy, null);

        builder.setTitle(R.string.dialog_privacy)
                .setView(viewInflater)
                .setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText voornaam = (EditText) viewInflater.findViewById(R.id.voornaam);
                        EditText mail = (EditText) viewInflater.findViewById(R.id.mail);
                        CheckBox allow = (CheckBox) viewInflater.findViewById(R.id.acceptPrivacy);

                        userSettingsLocal = LocalStorage.getUserSettingsLocal(getApplicationContext());

                        voornaam.setText(userSettingsLocal.getFirstName());
                        mail.setText(userSettingsLocal.getMail());

                        if (!allow.isChecked()) {
                            Toast.makeText(getBaseContext(), "Het privacybeleid moet geaccepteerd worden!", Toast.LENGTH_LONG).show();

                            showPrivacyDialog();
                        } else {
                            Toast.makeText(getBaseContext(), "Instellingen aangepast!", Toast.LENGTH_SHORT).show();
                            editUserSettings(voornaam, mail, allow);

                            dialog.cancel();
                        }

                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //feedback doorverwijzen naar database
    private void giveFeedback() {
        Toast.makeText(getBaseContext(), "Bedankt voor de feedback!", Toast.LENGTH_SHORT).show();
    }

    private void selectItem(int position) {

        // update the main content by replacing fragments
        final Fragment fragment;

        switch (position) {
            case 0:
                MapFragment.Builder builder = new MapFragment.Builder()
                        .setMapKey(Application.MAP_KEY);
                MapOptions mapOptions = MapOptions.getDefaultOptions();
                // Turn off the overview button (only shown if there is an overview map for the location)
                mapOptions.HIDE_OVERVIEW_BUTTON = true;
                mapOptions.HIDE_LEVELS_CONTROL = true;
                mapOptions.HIDE_MAP_LABEL = true;
                mapOptions.HIDE_WATERMARK = true;
                mapOptions.HIDE_ACCESSIBILITY_BUTTON = true;
                builder.setMapOptions(mapOptions);

                // example: how to set placemark markers text size
                /*
                    MapOptions mapOptions = ((MapFragment) fragment).getMapOptions();
                    mapOptions.setTextSize(14);
                    builder.setMapOptions(mapOptions);
                */
                // example: how to start directions programmatically

                final MapFragment mapFragment = builder.build();
                mapFragment.setMapEventListener(new MapView.MapEventListener() {

                    @Override
                    public void onMapLoadFinish() {

                    }

                    @Override
                    public void onMapLoadStart() {

                    }

                    @Override
                    public void onPlacemarksLoadFinish() {
                        if (afdeling != null) {
                            for (Placemark placemark : mapFragment.getMapView().getPlacemarks()) {
                                if (afdeling.equals(placemark.getName())) {
                                    mapFragment.startDirections(DirectionsDestination.forPlacemarkKey(placemark.getKey()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onMapRenderFinish() {

                    }

                    @Override
                    public void onMapLoadFail(Throwable tr) {
                        if (mapFragment.isAdded() && mapFragment.getActivity() != null) {
                            String message = getString(com.arubanetworks.meridian.R.string.mr_error_invalid_map);
                            if (tr != null) {
                                if (tr instanceof VolleyError && ((VolleyError) tr).networkResponse != null && ((VolleyError) tr).networkResponse.statusCode == 401) {
                                    message = "HTTP 401 Error: Please verify the Editor token.";
                                } else if (!Strings.isNullOrEmpty(tr.getLocalizedMessage())) {
                                    message = tr.getLocalizedMessage();
                                }
                            }
                            new AlertDialog.Builder(mapFragment.getActivity())
                                    .setTitle(getString(com.arubanetworks.meridian.R.string.mr_error_title))
                                    .setMessage(message)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(com.arubanetworks.meridian.R.string.mr_ok, null)
                                    .show();
                        }
                    }

                    @Override
                    public void onMapTransformChange(Matrix transform) {

                    }

                    @Override
                    public void onLocationUpdated(MeridianLocation location) {

                    }

                    @Override
                    public void onOrientationUpdated(MeridianOrientation orientation) {

                    }

                    @Override
                    public boolean onLocationButtonClick() {
                        // example of how to override the behavior of the location button
                        final MapView mapView = mapFragment.getMapView();
                        MeridianLocation location = mapView.getUserLocation();
                        if (location != null) {
                            mapView.updateForLocation(location);

                        } else {
                            LocationRequest.requestCurrentLocation(getApplicationContext(), Application.APP_KEY, new LocationRequest.LocationRequestListener() {
                                @Override
                                public void onResult(MeridianLocation location) {
                                    mapView.updateForLocation(location);
                                }

                                @Override
                                public void onError(LocationRequest.ErrorType location) {
                                    // handle the error
                                }
                            });
                        }
                        return true;
                    }
                });
                fragment = mapFragment;
                break;
            default:
                return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_map:
                Toast.makeText(getBaseContext(), "Map", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_feedback:
                showFeedbackDialog();
                break;
            case R.id.nav_privacy:
                String base_url = "https://privacybeleid.jordyliebens.be/";

                Uri uri = Uri.parse(base_url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.nav_productOverview:
                Intent intent2 = new Intent(this, ProductOverview.class);
                startActivity(intent2);
                break;
            case R.id.nav_instellingen:
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivity(intent3);
                break;
            default:
                return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    public void editUserSettings(EditText voornaam, EditText mail, CheckBox allow) {
        UserSettings userSettings = new UserSettings();

        if (allow.isChecked()) {
            userSettings.setAllowPrivacy(true);
        }
        userSettings.setFirstName(voornaam.getText().toString());
        userSettings.setMail(mail.getText().toString());

        LocalStorage.setLocalUserSettings(userSettings, getApplicationContext());
    }

    public void onClickPrivacyButton(View v) {
        String base_url = "https://privacybeleid.jordyliebens.be/";

        Uri uri = Uri.parse(base_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
