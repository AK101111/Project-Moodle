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


public class gradeFetcher {
    private static final String TAG = listCoursesRegFetcher.class.getSimpleName();
    private RequestQueue gradeListQueue;
    private User user;
    public gradeFetcher(RequestQueue requestQueue, User userModel){
        gradeListQueue = requestQueue;
        user = userModel;
    }
    public void getCoursesList(String baseUrl){
        String requestUrl = baseUrl + ApiUrls.GRADES;
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
        LinkedHashMap[] courses;
        LinkedHashMap[] grades;
        courses = Utils.jsonArrayToHashMap(jsonObject.getJSONArray("courses"));
        grades = Utils.jsonArrayToHashMap(jsonObject.getJSONArray("grades"));
        ArrayList<LinkedHashMap>[] Grades = mergeCoursesGrades(courses, grades, user.access_courses());
        user.update_grades(Grades);
    }

    private ArrayList<LinkedHashMap>[] mergeCoursesGrades(LinkedHashMap[] courses, LinkedHashMap[] grades, LinkedHashMap[] reg_courses) {
        // assert courses.length==grades.length: "Invalid response from server";
        ArrayList<LinkedHashMap>[] Grades = (ArrayList<LinkedHashMap>[])new ArrayList[reg_courses.length];
        int index;
        for(int i=0; i<courses.length; ++i) {
            index = Utils.findinCoursesArray(courses[i].get("code"), reg_courses);
            // assert index>=0: "Invalid response of grades";
            Grades[i].add(reg_courses[index]);
        }
        return Grades;
    }
}