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

    public static MoodleOnMobile App;
    private static RequestQueue requestQueue;
    private static User user;

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public static void setUser(User newUser) {
        //TODO save new user to disk
        user = newUser;
    }
    public static User getUser() {
        //TODO check if user is null then retrive from disk
        return user;
    }

    /**
     * Gets the moodle url
     * @return Moodle Url
     */
    public String getMoodleUrl() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("moodle_url","NULL");
    }
    public void setCookie(String cookie) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("cookie", cookie).commit();
    }
    public String getCookie() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("cookie","NULL");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        App = this;
        requestQueue = Volley.newRequestQueue(this);
    }
}
