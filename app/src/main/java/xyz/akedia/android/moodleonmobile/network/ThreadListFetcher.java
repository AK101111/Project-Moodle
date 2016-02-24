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

import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.ThreadDetailsActivity;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.Course;

/**
 * Created by arnavkansal on 24/02/16.
 */

/*
{
assignments: [ ],
registered: {
starting_date: "2016-01-01 00:00:00",
id: 1,
professor: 5,
semester: 2,
ending_date: "2016-05-10 00:00:00",
year_: 2016,
course_id: 1
},
course_threads: [
{
user_id: 5,
description: "aksjfnakjfsnasfjnaskjfn",
title: "ABC",
created_at: "2016-02-21 18:30:02",
registered_course_id: 1,
updated_at: "2016-02-21 18:30:02",
id: 1
}
],
course: {
code: "cop290",
name: "Design Practices in Computer Science",
description: "Design Practices in Computer Science.",
credits: 3,
id: 1,
l_t_p: "0-0-6"
},
grades: [ ],
tab: "threads",
year: 2016,
sem: 2,
resources: [ ],
previous: [ ]
}
 */
public class ThreadListFetcher {
    private static final String TAG = ThreadListFetcher.class.getSimpleName();

    private final String threadListUrl;
    private final RequestQueue requestQueue;
    private final ThreadListResponseHandler responseHandler;
    private final String cookie;

    public interface ThreadListResponseHandler{
        void onSuccess(int currentSem, JSONArray threadList);
        void onFailure();
        void onError(Exception e);
    }

    public ThreadListFetcher(ThreadListResponseHandler threadListResponseHandler){
        threadListUrl = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.COURSE_BASE + CourseDetailsActivity.courseCode.toLowerCase() + "/threads";
        requestQueue = MoodleOnMobile.getRequestQueue();
        responseHandler = threadListResponseHandler;
        cookie = MoodleOnMobile.App.getCookie();
    }

    public void getThreadList(){
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "received response : " + response);
                    int currentSem = response.getInt("sem");
                    JSONArray threadList = response.getJSONArray("course_threads");
                    responseHandler.onSuccess(currentSem,threadList);
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
            JsonObjectRequest courseListRequest = new JsonObjectRequest(Request.Method.GET, threadListUrl, null, responseListener, errorListener) {
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
