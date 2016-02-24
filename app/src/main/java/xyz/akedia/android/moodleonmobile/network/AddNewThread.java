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

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;

/**
 * Created by ashish on 24/2/16.
 */
public class AddNewThread {
    private static final String TAG = AssignmentFetcher.class.getSimpleName();
    private final String newThreadUrl;
    private RequestQueue requestQueue;
    private final AddNewThreadResponseHandler responseHandler;
    private final String cookie;

    public interface AddNewThreadResponseHandler{
        void onSuccess(JSONObject response);
        void onFailure();
        void onError(Exception e);
    }

    public AddNewThread(AddNewThreadResponseHandler addNewThreadResponseHandler,String title,String description,String courseCode){
        newThreadUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.THREAD_NEW
                + "title=" + URLEncoder.encode(title) + "&description=" + URLEncoder.encode(description) + "&course_code=" + URLEncoder.encode(courseCode.toLowerCase());
        Log.d(TAG, "addNewThread response : " + newThreadUrl);
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler = addNewThreadResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getAddNewThreadResponse(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "addNewThread response : " + response);
                    responseHandler.onSuccess(response);
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
            JsonObjectRequest addNewThreadRequest = new JsonObjectRequest(Request.Method.GET, newThreadUrl, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
            requestQueue.add(addNewThreadRequest);
        } catch (Exception e) {
            responseHandler.onError(e);
        }
    }
}
