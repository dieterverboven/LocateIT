package be.thomasmore.locateit.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.UserSettings;
import be.thomasmore.locateit.services.LocalStorage;
import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserSettings userSettingsLocal = LocalStorage.getUserSettingsLocal(getApplicationContext());
        String naam = "gebruiker";

        if (userSettingsLocal.getFirstName() != null)
        {
            naam = userSettingsLocal.getFirstName();
        }

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.WHITE)
                .withLogo(R.drawable.logo)
                .withAfterLogoText("Welkom, " + naam + "!")
                .withFooterText("Copyright LocateIT");


        View view = config.create();


        setContentView(view);



    }
}
