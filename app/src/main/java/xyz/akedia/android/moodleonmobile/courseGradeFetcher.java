package xyz.akedia.android.moodleonmobile;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by arnavkansal on 15/02/16.
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
        course_threads: [ ],
        course: {
        code: "cop290",
        name: "Design Practices in Computer Science",
        description: "Design Practices in Computer Science.",
        credits: 3,
        id: 1,
        l_t_p: "0-0-6"
        },
        grades: [
        {
        weightage: 10,
        user_id: 1,
        name: "Assignment 1",
        out_of: 15,
        registered_course_id: 1,
        score: 15,
        id: 1
        },
        {
        weightage: 15,
        user_id: 1,
        name: "Assignment 2",
        out_of: 20,
        registered_course_id: 1,
        score: 10,
        id: 2
        },
        {
        weightage: 25,
        user_id: 1,
        name: "Minor 1",
        out_of: 30,
        registered_course_id: 1,
        score: 25,
        id: 3
        }
        ],
        tab: "grades",
        year: 2016,
        sem: 2,
        resources: [ ],
        previous: [ ]
        }
*/
public class courseGradeFetcher {
    private static final String TAG = listCoursesRegFetcher.class.getSimpleName();
    private RequestQueue gradeListQueue;
    private User user;
    private String coursecode;
    public courseGradeFetcher(RequestQueue requestQueue, User userModel, String requestCourse){
        gradeListQueue = requestQueue;
        user = userModel;
        coursecode = requestCourse;
    }
    public void getCoursesList(String baseUrl){
        String requestUrl = baseUrl + ApiUrls.COURSE_BASE+coursecode+"/grades";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            validateParseUpdateResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyError.
            }
        }
        );
        //Log.d()
        gradeListQueue.add(jsonObjectRequest);
    }

    // Assumes listCoursesRegFetcher called before gradeFetcher.
    private void validateParseUpdateResponse(JSONObject jsonObject) throws JSONException {
        user.update_gradeList(coursecode, jsonObject);
    }
}