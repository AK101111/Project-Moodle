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
 * Created by akedia on 24/02/16.
 */
public class SendComment {
    private static final String TAG = SendComment.class.getSimpleName();

    private final String url;
    private final RequestQueue requestQueue;
    private final ResponseHandler responseHandler;
    private final String cookie;

    public interface ResponseHandler{
        void startWait();
        void finishWait();
        void onSuccess();
        void onFailure();
    }

    public SendComment(int threadId, String description, ResponseHandler responseHandler){
        url = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.ADD_COMMENT + String.format("?thread_id=%d&description=%s",threadId, URLEncoder.encode(description));
        requestQueue = MoodleOnMobile.getRequestQueue();
        this.responseHandler = responseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void send(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    responseHandler.finishWait();
                    boolean isSuccessful = response.getBoolean("success");
                    if(isSuccessful)
                        responseHandler.onSuccess();
                    else
                        responseHandler.onFailure();
                } catch (Exception e) {
                    e.printStackTrace();
                    responseHandler.finishWait();
                    responseHandler.onFailure();
                }
            }
        };
        Response.ErrorListener errorListener =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "some error occured : " + error);
                responseHandler.finishWait();
                responseHandler.onFailure();
            }
        };
        responseHandler.startWait();
        try {
            JsonObjectRequest courseListRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
            requestQueue.add(courseListRequest);
        } catch (Exception e) {
            responseHandler.finishWait();
            responseHandler.onFailure();
        }
    }
}
