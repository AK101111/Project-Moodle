package xyz.akedia.android.moodleonmobile.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;

/**
 * Created by akedia on 24/02/16.
 */
public class Users {
    public static Map<Integer,String> userIdNamesMapping = new HashMap<>();
    private static String getNameFromNetwork(int userId) {
        String url = MoodleOnMobile.App.getMoodleUrl() + "/users/user.json/" + Integer.toString(userId);
        final StringBuilder name = new StringBuilder();
        final StringBuilder isComplete = new StringBuilder();
        final JsonObjectRequest requestUser = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject userData = response.getJSONObject("user");
                    String firstName = userData.getString("first_name");
                    String lastName = userData.getString("last_name");
                    name.append(firstName + " " + lastName);
                    isComplete.append("done");
                } catch (Exception e) {
                    e.printStackTrace();
                    isComplete.append("done");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isComplete.append("done");
            }
        });
        while(!isComplete.toString().equals("done")) {}
        if(name.toString().isEmpty())
            return "Unknown user";
        userIdNamesMapping.put(userId,name.toString());
        return name.toString();
    }
    public static String getName(int userId) {
        String name = userIdNamesMapping.get(userId);
        if(name != null)
            return name;
        else
            return getNameFromNetwork(userId);
    }
}
