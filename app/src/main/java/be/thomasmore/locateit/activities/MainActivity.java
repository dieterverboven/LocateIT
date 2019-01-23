package be.thomasmore.locateit.activities;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.Feedback;
import be.thomasmore.locateit.helpers.JsonHelper;
import be.thomasmore.locateit.http.HttpWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_feedback:
                showFeedbackDialog();
                return true;
            default:
                return false;
        }
    }

    private void showFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.dialog_feedback, null);
        builder.setTitle(R.string.dialog_feedback)
                .setView(viewInflater)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

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

    //feedback doorverwijzen naar database
    private void giveFeedback() {
        Date date = new Date();

        EditText beschrijving = (EditText) findViewById(R.id.beschrijving);
        Spinner typeSpinner = (Spinner) findViewById(R.id.starsSpinner);

        Feedback feedback = new Feedback();

        feedback.setTimestamp((int)date.getTime());
        feedback.setScore(typeSpinner.getSelectedItemId() + 1);
        feedback.setBeschrijving(beschrijving.getText().toString());

        //POST request naar backend
        JsonHelper jsonHelper = new JsonHelper();
        HttpWriter httpWriter = new HttpWriter();
        httpWriter.setJsonObject(jsonHelper.getJsonFeedback(feedback));

        httpWriter.execute("https://locateit-backend.herokuapp.com/feedback");

        Toast.makeText(getBaseContext(),"Bedankt voor de feedback!",Toast.LENGTH_SHORT).show();
    }
}
