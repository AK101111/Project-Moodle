package xyz.akedia.android.moodleonmobile;

import android.app.Notification;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.Notifications;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by arnavkansal on 20/02/16.
 */
public class notificationsFetcher {
    private static final String TAG = notificationsFetcher.class.getSimpleName();
    private RequestQueue notificationQueue;
    private User user;
    private String courseCode;
    public notificationsFetcher(RequestQueue requestQueue, User userModel){
        notificationQueue = requestQueue;
        user = userModel;
    }
//    public void getNotifications(String baseUrl){
//        String requestUrl = baseUrl + ApiUrls.NOTIFICATIONS;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                requestUrl,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            validateParseUpdateResponse(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // VolleyError.
//            }
//        }
//        );
//        //Log.d()
//        notificationQueue.add(jsonObjectRequest);
//    }
    // TODO
//    public void validateParseUpdateResponse(JSONObject jsonObject) throws JSONException{
//        JSONArray notifications = jsonObject.getJSONArray("notifications");
//        Notifications[] updatedNotifications = new Notifications[notifications.length()];
//        for(int i=0; i<notifications.length(); ++i){
//            updatedNotifications[i] = Utils.jsonObjectToNotification(notifications.getJSONObject(i));
//        }
//        user.updateNotifications(updatedNotifications);
//    }
}

