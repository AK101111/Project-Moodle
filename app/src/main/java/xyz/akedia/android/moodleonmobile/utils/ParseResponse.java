package xyz.akedia.android.moodleonmobile.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.akedia.android.moodleonmobile.model.Course;
import xyz.akedia.android.moodleonmobile.model.CourseScheme;
import xyz.akedia.android.moodleonmobile.model.Notification;
import xyz.akedia.android.moodleonmobile.model.User;

/**
 * Created by akedia on 23/02/16.
 */
public class ParseResponse {
    private static final String TAG = ParseResponse.class.getSimpleName();

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

    public static Course parseCourseData(JSONObject courseData) throws JSONException{
        String code = courseData.getString("code");
        String name = courseData.getString("name");
        String description = courseData.getString("description");
        int credits = courseData.getInt("credits");
        int courseId = courseData.getInt("id");
        String ltp = courseData.getString("l_t_p");
        return new Course(code,name,description,credits,courseId,ltp);
    }

    public static ArrayList<Course> parseCourseList(JSONArray courseList) throws JSONException{
        ArrayList<Course> courses = new ArrayList<>(courseList.length());
        for(int i = 0; i < courseList.length(); i++) {
            courses.add(parseCourseData(courseList.getJSONObject(i)));
        }
        return courses;
    }

//    public static Notification parseNotification(JSONObject notificationData) {
//
//    }
}
