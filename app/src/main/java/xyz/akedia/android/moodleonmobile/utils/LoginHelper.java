package xyz.akedia.android.moodleonmobile.utils;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;

/**
 * does login on moodle and executes an handler
 */
public class LoginHelper {
    private static final String TAG = LoginHelper.class.getSimpleName();
    private final String loginUrl;
    private final RequestQueue requestQueue;
    private final String requestTag;
    private final LoginResponseHandler loginResponseHandler;

    public interface LoginResponseHandler {
        void onSuccess(JSONObject user);
        void onFailure();
        void onError(Exception exception);
    }

    public void manageCookie(String cookie) {
        MoodleOnMobile.App.setCookie(cookie);
    }

    public LoginHelper(String username, String password, LoginResponseHandler loginResponseHandler) {
        this.loginUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.LOGIN + String.format("?userid=%s&password=%s",username,password);
        this.requestTag = String.format("Login{uid=%s}",username);
        this.requestQueue = MoodleOnMobile.getRequestQueue();
        this.loginResponseHandler = loginResponseHandler;
    }

    public void sendLoginRequest() {
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        loginResponseHandler.onSuccess(response.getJSONObject("user"));
                    else
                        loginResponseHandler.onFailure();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginResponseHandler.onError(error);
            }
        };
        try {
            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET,loginUrl,null,responseListener,errorListener){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    String cookie =  response.headers.get("Set-Cookie");
                    Log.d(TAG,"Login cookie : "+cookie);
                    manageCookie(cookie);
                    return super.parseNetworkResponse(response);
                }
            };
            loginRequest.setTag(requestTag);
            requestQueue.add(loginRequest);
        } catch (Exception e) {
            loginResponseHandler.onError(e);
        }
    }
    public void cancelLogin() {
        requestQueue.cancelAll(requestTag);
    }
}
