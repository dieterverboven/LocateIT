package be.thomasmore.locateit.services;

import android.content.Context;

import be.thomasmore.locateit.classes.UserSettings;

public class LocalStorage {
    public static UserSettings getUserSettingsLocal(Context context) {
        UserSettings userSettings = new UserSettings();

        return userSettings;
    }

        public static boolean setLocalUserSettings(UserSettings userSettings, Context context) {


        return true;
    }
}
