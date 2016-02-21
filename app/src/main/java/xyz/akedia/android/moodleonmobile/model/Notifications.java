package xyz.akedia.android.moodleonmobile.model;

import android.app.Notification;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnavkansal on 21/02/16.
 */
public class Notifications {
    private String user_id;
    private int is_seen;
    private String created_at;
    private String id;
    private String description;
//    private String posted_by;
//    private String thread_link;
//    private String courseid;

    public void set_init(JSONObject notification) throws JSONException{
        user_id = notification.getString("user_id");
        description = notification.getString("description");
        is_seen = notification.getInt("is_seen");
        created_at = notification.getString("created_at");
        id = notification.getString("id");
        update_description(notification.getString("description"));
    }

    protected void update_description(String description) {
        //TODO use jsoup?
    }

}
