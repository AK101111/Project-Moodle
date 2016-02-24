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
 * Created by akedia on 24/02/16.
 */
public class ThreadInfoFetcher {
    private static final String TAG = ThreadInfoFetcher.class.getSimpleName();

    private final String threadInfoUrl;
    private final RequestQueue requestQueue;
    private final ThreadInfoResponseHandler responseHandler;
    private final String cookie;

    public interface ThreadInfoResponseHandler{
        void onSuccess(JSONObject courseData, JSONObject threadData, JSONArray comments, JSONArray updateTime, JSONArray commentUsers);
        void onFailure();
        void onError(Exception e);
    }

    public ThreadInfoFetcher(int threadId, ThreadInfoResponseHandler threadInfoResponseHandler){
        threadInfoUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.THREAD_INFO_BASE + Integer.toString(threadId);
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler =threadInfoResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getThreadInfo(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "received response : " + response);
                    JSONObject courseData = response.getJSONObject("course");
                    JSONObject threadData = response.getJSONObject("thread");
                    JSONArray comments = response.getJSONArray("thread");
                    JSONArray updateTime = response.getJSONArray("times_readable");
                    JSONArray commentUsers = response.getJSONArray("comment_users");
                    responseHandler.onSuccess(courseData,threadData,comments,updateTime,commentUsers);
                } catch (Exception e) {
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
            JsonObjectRequest courseListRequest = new JsonObjectRequest(Request.Method.GET, threadInfoUrl, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
            requestQueue.add(courseListRequest);
        } catch (Exception e) {
            responseHandler.onError(e);
        }
    }
}
