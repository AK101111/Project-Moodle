package xyz.akedia.android.moodleonmobile;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.utils.Utils;

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

public class listCoursesRegFetcher{
    private static final String TAG = listCoursesRegFetcher.class.getSimpleName();
    private RequestQueue courseListQueue;
    private User user;
    public listCoursesRegFetcher(RequestQueue requestQueue, User userModel){
        courseListQueue = requestQueue;
        user = userModel;
    }
    public void getCoursesList(String baseUrl){
        String requestUrl = baseUrl + ApiUrls.COURSES;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            validateAndParseResponse(response);
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
        courseListQueue.add(jsonObjectRequest);
    }

    private void validateAndParseResponse(JSONObject jsonObject) throws JSONException {
        user.update_listCourses(jsonObject);
    }
}