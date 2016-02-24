package xyz.akedia.android.moodleonmobile.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;

/**
 * Created by ashish on 24/2/16.
 */
public class AssignmentFetcher {
    private static final String TAG = AssignmentFetcher.class.getSimpleName();
    private final String assignmentUrl;
    private RequestQueue requestQueue;
    private final AssignmentResponseHandler responseHandler;
    private final String cookie;

    public interface AssignmentResponseHandler{
        void onSuccess(JSONArray assignments);
        void onFailure();
        void onError(Exception e);
    }

    public AssignmentFetcher(AssignmentResponseHandler assignmentResponseHandler,String courseCode){
        assignmentUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.COURSE_BASE
                                + courseCode +"/assignments";
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler = assignmentResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getAssignments(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "assignments response : " + response);
                    JSONArray assignments = response.getJSONArray("assignments");
                    responseHandler.onSuccess(assignments);
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
            JsonObjectRequest assignmentRequest = new JsonObjectRequest(Request.Method.GET, assignmentUrl, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
            requestQueue.add(assignmentRequest);
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
