package xyz.akedia.android.moodleonmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

//import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by arnavkansal on 20/02/16.
 */
public class Grades {
    public String weightage;
    public String courseCode;
    //private String user_id;
    public String name;
    private String outOf;
    private String scoreOutOf;
//    private String registered_course_id;
    public String score;
    //public String id;

    public Grades(String weight, String n, String scoreOf, String of){
        weightage = weight;
        name = n;
        outOf = of;
        scoreOutOf = scoreOf;
        score = scoreOutOf+"/"+outOf;
    }
    public void setCourseCode(String code){
        courseCode = code;
    }

//    public void update(JSONObject jsonObject) throws JSONException{
//        set_init(Utils.jsonObjectToHashMap(jsonObject));
//    }
}
