package xyz.akedia.android.moodleonmobile.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

//import xyz.akedia.android.moodleonmobile.config.ApiUrls;
//import xyz.akedia.android.moodleonmobile.model.Notifications;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.User;

/**
 * Created by arnavkansal on 20/02/16.
 */
public class NotificationsFetcher {
    private static final String TAG = NotificationsFetcher.class.getSimpleName();
    private final String notificationUrl;
    private RequestQueue requestQueue;
    private final NotificationsResponseHandler responseHandler;
    private final String cookie;

    public interface NotificationsResponseHandler{
        void onSuccess(JSONArray notifications);
        void onFailure();
        void onError(Exception e);
    }

    public NotificationsFetcher(NotificationsResponseHandler notificationsResponseHandler){
        notificationUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.NOTIFICATIONS;
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler = notificationsResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getNotifications(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "notifications response : " + response);
                    JSONArray notifications = response.getJSONArray("notifications");
                    responseHandler.onSuccess(notifications);
                } catch (Exception e) {
                    Log.d(TAG,"Error : " + e);
                    e.printStackTrace();
                    responseHandler.onError(e);
                }
            }
        };
        Response.ErrorListener errorListener =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"some error occured : " + error);
                responseHandler.onError(error);
            }
        };
        try {
            JsonObjectRequest notificationsRequest = new JsonObjectRequest(Request.Method.GET, notificationUrl, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
            requestQueue.add(notificationsRequest);
        } catch (Exception e) {
            responseHandler.onError(e);
        }
    }
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

