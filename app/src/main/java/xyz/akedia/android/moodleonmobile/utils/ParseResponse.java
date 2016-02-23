package xyz.akedia.android.moodleonmobile.utils;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.model.User;

/**
 * Created by akedia on 23/02/16.
 */
public class ParseResponse {
    public static User parseUserData(JSONObject userData) throws JSONException{
        int userId = userData.getInt("id");
        String firstName = userData.getString("first_name");
        String lastName = userData.getString("last_name");
        String userName = userData.getString("username");
        String entryNumber = userData.getString("entry_no");
        String email = userData.getString("email");
        boolean isStudent = (userData.getInt("type_") == 0);
        return new User(userId,firstName,lastName,userName,entryNumber,email,isStudent);
    }
}
