package be.thomasmore.locateit.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.UserSettings;
import be.thomasmore.locateit.services.LocalStorage;

public class SettingsActivity extends AppCompatActivity {
    EditText editNaam;
    EditText editMail;
    CheckBox allow;
    UserSettings userSettingsLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userSettingsLocal = LocalStorage.getUserSettingsLocal(getApplicationContext());

        editNaam = (EditText) findViewById(R.id.voornaam);
        editMail = (EditText) findViewById(R.id.mail);
        allow = (CheckBox) findViewById(R.id.acceptPrivacy);

        if (userSettingsLocal.isAllowPrivacy())
        {
            allow.setChecked(true);
        }
        editNaam.setText(userSettingsLocal.getFirstName());
        editMail.setText(userSettingsLocal.getMail());
    }

    public void onClickSaveButton(View v)
    {
        if (allow.isChecked())
        {
            userSettingsLocal.setAllowPrivacy(true);
        }
        else
        {
            userSettingsLocal.setAllowPrivacy(false);
        }
        userSettingsLocal.setFirstName(editNaam.getText().toString());
        userSettingsLocal.setMail(editMail.getText().toString());

        LocalStorage.setLocalUserSettings(userSettingsLocal, getApplicationContext());

        this.finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickPrivacyButton(View v)
    {
        String base_url = "https://privacybeleid.jordyliebens.be/";

        Uri uri = Uri.parse(base_url);
        Intent intent =new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
