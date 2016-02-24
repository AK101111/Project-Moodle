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
public class GradeFetcher {
    private static final String TAG = GradeFetcher.class.getSimpleName();
    private final String gradeUrl;
    private RequestQueue requestQueue;
    private final GradeResponseHandler responseHandler;
    private final String cookie;

    public interface GradeResponseHandler{
        void onSuccess(JSONArray grades);
        void onFailure();
        void onError(Exception e);
    }

    public GradeFetcher(GradeResponseHandler gradeResponseHandler,String courseCode){
        gradeUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.COURSE_THREADS_BASE
                + courseCode +"/grades";
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler = gradeResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getGradeFetcher(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "grades response : " + response);
                    JSONArray grades = response.getJSONArray("grades");
                    responseHandler.onSuccess(grades);
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
            JsonObjectRequest gradeRequest = new JsonObjectRequest(Request.Method.GET, gradeUrl, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
            requestQueue.add(gradeRequest);
        } catch (Exception e) {
            responseHandler.onError(e);
        }
    }
}