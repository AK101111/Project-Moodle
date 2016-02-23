package xyz.akedia.android.moodleonmobile;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.Assignment;
import xyz.akedia.android.moodleonmobile.model.User;

/**
 * Created by arnavkansal on 20/02/16.
 */

//TODO
public class particularAssignment {
    private static final String TAG = particularAssignment.class.getSimpleName();
    private RequestQueue particularAssgnQueue;
    private User user;
    private String assgncode;

    public particularAssignment(RequestQueue requestQueue, User userModel) {
        particularAssgnQueue = requestQueue;
        user = userModel;
    }

//    public void getAssignment(String baseUrl, String assgnCode) {
//        assgncode = assgnCode;
//        String requestUrl = baseUrl + ApiUrls.ASSIGNMENT_BASE + assgncode;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                requestUrl,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            validateParseUpdateResponse(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // VolleyError.
//            }
//        }
//        );
//        //Log.d()
//        particularAssgnQueue.add(jsonObjectRequest);
//    }

//    public void validateParseUpdateResponse(JSONObject response) throws JSONException{
//        user.updatefindAssignmentbyCode(response.getJSONObject("assignment"));
//    }
}