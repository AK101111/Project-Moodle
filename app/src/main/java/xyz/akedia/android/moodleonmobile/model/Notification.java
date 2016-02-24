package xyz.akedia.android.moodleonmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnavkansal on 21/02/16.
 */
public class Notification {
    private String userIdPoster;
    private int isSeen;
    private String createdAt;
    private String id;
    private String description;
//    private String posted_by;
//    private String thread_link;
//    private String courseid;



    public void set_init(JSONObject notification) throws JSONException{
        userIdPoster = notification.getString("user_id");
        description = notification.getString("description");
        isSeen = notification.getInt("is_seen");
        createdAt = notification.getString("created_at");
        id = notification.getString("id");
//        update_description(notification.getString("description"));
    }

//    protected void update_description(String description) {
//        //TODO use jsoup?
//    }

}
