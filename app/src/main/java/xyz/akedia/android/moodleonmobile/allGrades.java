//package xyz.akedia.android.moodleonmobile;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import xyz.akedia.android.moodleonmobile.config.ApiUrls;
//import xyz.akedia.android.moodleonmobile.model.User;
//
///**
// * Created by arnavkansal on 21/02/16.
// */
//public class allGrades {
//    private static final String TAG = allGrades.class.getSimpleName();
//    private RequestQueue GradesQueue;
//    private User user;
//    public allGrades(RequestQueue requestQueue, User userModel){
//        GradesQueue = requestQueue;
//        user = userModel;
//    }
//    public void getGrades(String baseUrl){
//        String requestUrl = baseUrl + ApiUrls.GRADES;
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
//        GradesQueue.add(jsonObjectRequest);
//    }
//
////    private void validateParseUpdateResponse(JSONObject jsonObject) throws JSONException {
////        user.updateAllgrades(jsonObject);
////    }
//}
//
