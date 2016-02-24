package xyz.akedia.android.moodleonmobile.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import xyz.akedia.android.moodleonmobile.model.Assignment;
//import xyz.akedia.android.moodleonmobile.model.Courses;
import xyz.akedia.android.moodleonmobile.model.Grades;
//import xyz.akedia.android.moodleonmobile.model.Notifications;

/**
 * * Created by arnavkansal on 15/02/16.
 */
public class Utils {
//    public static LinkedHashMap[] jsonArrayToHashMap(JSONArray jsonArray) throws JSONException {
//        LinkedHashMap[] HashMapArray;
//        HashMapArray = new LinkedHashMap[jsonArray.length()];
//        for (int i = 0; i < jsonArray.length(); i++) {
//            HashMapArray[i] = jsonObjectToHashMap(jsonArray.getJSONObject(i));
//        }
//        return HashMapArray;
//    }
//    public static LinkedHashMap<String,String> jsonObjectToHashMap(JSONObject jsonObject) throws JSONException {
//        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//        Iterator<String> keysItr = jsonObject.keys();
//        while(keysItr.hasNext()) {
//            String key = keysItr.next();
//            String value = jsonObject.getString(key);
//            map.put(key, value);
//        }
//        return map;
//    }
//
//    public static int findinCoursesArray(Object value, LinkedHashMap[] Data) {
//        for (int i = 0; i < Data.length; i++) {
//            if(Data[i].get("code") == value) return i;
//        }
//        return -1;
//    }

//    {
//        code: "cop290",
//                name: "Design Practices in Computer Science",
//            description: "Design Practices in Computer Science.",
//            credits: 3,
//            id: 1,
//            l_t_p: "0-0-6"
//    }
//    public static Courses jsonObjectToCourseinfo(JSONObject jsonObject) throws JSONException {
//        LinkedHashMap<String, String> courseDetails = jsonObjectToHashMap(jsonObject);
//        Courses course = new Courses();
//        course.set_init(courseDetails);
//        return course;
//    }
//
//    public static Grades jsonObjectToGrades(JSONObject jsonObject) throws JSONException{
//        LinkedHashMap<String, String> gradeList = jsonObjectToHashMap(jsonObject);
//        Grades grades = new Grades();
//        grades.set_init(gradeList);
//        return grades;
//    }
//
//    public static Assignment jsonObjectToAssignment(JSONObject jsonObject) throws JSONException {
//        LinkedHashMap<String, String> assignmentList = jsonObjectToHashMap(jsonObject);
//        Assignment assignment = new Assignment();
//        assignment.set_init(assignmentList);
//        return assignment;
//    }
//
//    public static Notifications jsonObjectToNotification(JSONObject notification) throws JSONException {
//        Notifications retnotification = new Notifications();
//        retnotification.set_init(notification);
//        return retnotification;
//    }
    public static String parseDate(String oldDate){
        String date = oldDate;
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate);
            date = new SimpleDateFormat("dd-MM-yyyy").format(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

}
