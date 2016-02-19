package xyz.akedia.android.moodleonmobile.app;

import android.app.Application;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import xyz.akedia.android.moodleonmobile.model.User;

/**
 * Global Application context
 */
public class MoodleOnMobile extends Application{

    public static RequestQueue requestQueue;

    /**
     * Gets the moodle url
     * @return Moodle Url
     */
    public static User userModel;
    public String getMoodleUrl() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("moodle_url","NULL");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        userModel = new User();
    }
}
