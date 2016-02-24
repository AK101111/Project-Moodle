package xyz.akedia.android.moodleonmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnavkansal on 21/02/16.
 */
public class Notification {
    private String userId;
    private int isSeen;
    public String createdAt;
    private String notificationId;
    public String description;
//    private String posted_by;
//    private String thread_link;
//    private String courseid;

    public Notification(String uId, String desc,int seen,String time,String id){
        userId = uId;
        description = desc;
        isSeen = seen;
        createdAt = time;
        notificationId = id;
        updateDescription(description);
    }

    protected void updateDescription(String description) {
        //TODO use jsoup?
    }

}
