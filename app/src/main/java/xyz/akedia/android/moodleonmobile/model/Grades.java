package xyz.akedia.android.moodleonmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

//import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by arnavkansal on 20/02/16.
 */
public class Grades {
    private String weightage;
    private String user_id;
    private String name;
    private String out_of;
    private String registered_course_id;
    private String score;
    public String id;

    public void set_init(LinkedHashMap<String, String> grades){
        weightage = grades.get("weightage");
        user_id = grades.get("user_id");
        name = grades.get("name");
        out_of = grades.get("out_of");
        registered_course_id = grades.get("registered_course_id");
        score = grades.get("score");
        id = grades.get("id");
    }

//    public void update(JSONObject jsonObject) throws JSONException{
//        set_init(Utils.jsonObjectToHashMap(jsonObject));
//    }
}
