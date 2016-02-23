package xyz.akedia.android.moodleonmobile.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;

/**
 * Created by arnavkansal on 14/02/16.
 */


/*      DATA FORMAT of JSON Request received
current_sem: 2,
courses: [
    {
        code: "cop290",
        name: "Design Practices in Computer Science",
        description: "Design Practices in Computer Science.",
        credits: 3,
        id: 1,
        l_t_p: "0-0-6"
    },
    {
        code: "csl838",
        name: "Wireless Networks",
        description: "PHY and MAC layer concepts in wireless networking",
        credits: 3,
        id: 2,
        l_t_p: "2-0-2"
    }
],
user: {
    username: "cs1110200",
    first_name: "John",
    last_name: "Doe",
    entry_no: "2011CS10200",
    registration_id: "",
    id: 1,
    reset_password_key: "",
    type_: 0,
    registration_key: "",
    email: "cs1110200@cse.iitd.ac.in"
},
current_year: 2016
*/

public class CourseListFetcher {
    private static final String TAG = CourseListFetcher.class.getSimpleName();

    private final String courseListUrl;
    private final RequestQueue requestQueue;
    private final CourseListResponseHandler responseHandler;
    private String cookie;

    public interface CourseListResponseHandler{
        void onSuccess(int currentSem, JSONArray courseList, JSONObject user);
        void onFailure();
        void onError(Exception e);
    }

    public CourseListFetcher(CourseListResponseHandler courseListResponseHandler){
//        courseListUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.COURSELIST;
//        courseListUrl = "http://192.168.0.100:9000/courses/list.json";
//        courseListUrl = "http://192.168.0.100:8000/courses/list.json";
        courseListUrl = "http://192.168.0.100:8000/" + "/courses/list.json";
        Log.d(TAG,"moodleUrl is http://192.168.0.100:8000/ :" + MoodleOnMobile.App.getMoodleUrl().equals("http://192.168.0.100:8000/"));
        Log.d(TAG,"other part is /courses/list.json : " + ApiUrls.COURSELIST.equals("/courses/list.json"));
        Log.d(TAG,"other part is : " + ApiUrls.COURSELIST);
        String courseListUrl2 = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.COURSELIST;
        Log.d(TAG,"other url is :" + courseListUrl2);
        Log.d(TAG,"Both url same : " + courseListUrl.equals(courseListUrl2));
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler = courseListResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getCoursesList(){
        Log.d(TAG,"course list url : " + courseListUrl);
        cookie = cookie.split(";")[0];
        Log.d(TAG,"cookie used : " + cookie);
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG,"received response : " + response);
                    int currentSem = response.getInt("current_sem");
                    JSONArray courseList = response.getJSONArray("courses");
                    JSONObject user = response.getJSONObject("user");
                    responseHandler.onSuccess(currentSem,courseList,user);
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
            JsonObjectRequest courseListRequest = new JsonObjectRequest(Request.Method.GET, courseListUrl, null, responseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Cookie",cookie);
                    return headers;
                }
            };
//            StringRequest courseListRequest = new StringRequest(Request.Method.GET, courseListUrl, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.d(TAG,"course List response : " + response);
//                }
//            },errorListener) {
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    Log.d(TAG,"raw network response : " + response.toString());
//                    Log.d(TAG,"response headers : " + response.headers);
//                    Log.d(TAG,"response data : " + response.data);
//                    String resData = new String(response.data);
//                    Log.d(TAG,"response data as string : " + resData);
//                    return super.parseNetworkResponse(response);
//                }
//            };
            requestQueue.add(courseListRequest);
        } catch (Exception e) {
            responseHandler.onError(e);
        }
    }
}