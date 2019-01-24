package be.thomasmore.locateit.classes;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;

public class Application extends android.app.Application {
    //NOTE: To build the Kotlin Samples App, change the build variant.
    // To build the default Sample SDK App, use:
    public static final EditorKey APP_KEY = EditorKey.forApp("5995631481192448");
    public static final EditorKey MAP_KEY = EditorKey.forMap("5646874153320448", APP_KEY);

    // To build your own customized SDK based App, replace APP_KEY and MAP_KEY with your location's App and Map ID values:
    // public static final EditorKey APP_KEY = EditorKey.forApp("APP_KEY");
    // public static final EditorKey MAP_KEY = EditorKey.forMap("MAP_KEY", APP_KEY);

    // To build the default Sample SDK App for EU Servers, use the following:
    // NOTE: Even if you're geographically located in the EU, you probably won't need to do this.
    // public static final EditorKey APP_KEY = EditorKey.forApp("4856321132199936");
    // public static final EditorKey MAP_KEY = EditorKey.forMap("5752754626625536", APP_KEY);

    public static final String PLACEMARK_UID = "CASIO_UID"; // replace this with a unique id for one of your placemarks.
    public static final String CAMPAIGN_ID = ""; // unique id for one of your campaigns here.
    public static final String TAG_MAC = ""; // mac address of one of your tags here

    @Override
    public void onCreate() {
        // Configure Meridian
        Meridian.configure(this);

        super.onCreate();
    }
}
