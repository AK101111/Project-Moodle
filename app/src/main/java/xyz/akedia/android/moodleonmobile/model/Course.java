package xyz.akedia.android.moodleonmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

//import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by arnavkansal on 20/02/16.
 */
//
//{
//        code: "cop290",
//        name: "Design Practices in Computer Science",
//        description: "Design Practices in Computer Science.",
//        credits: 3,
//        id: 1,
//        l_t_p: "0-0-6"
//        }
public class Course {
    private String code;
    private String name;
    private String description;
    private int credits;
    public int courseId;
    private CourseScheme courseScheme;

    public Grades[] grades;
    public Assignment[] assignment;
    private courseResources[] resources;

    public Course(String code, String name, String description, int credits, int id, String ltp) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.courseId = id;
        this.courseScheme = CourseScheme.parseLtp(ltp);
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getCredits() {
        return credits;
    }
    public CourseScheme getCourseScheme() {
        return courseScheme;
    }

//    public void set_init(LinkedHashMap<String, String> courseDetails) {
//        code = courseDetails.get("code");
//        name = courseDetails.get("name");
//        description = courseDetails.get("description");
//        credits = courseDetails.get("credits");
//        id = courseDetails.get("id");
//        credits_scheme = courseDetails.get("credits_scheme");
//    }

    public void set_grades(Grades[] gradesList){
        grades = gradesList;
    }

    public void set_assignment(Assignment[] assignmentList){
        assignment = assignmentList;
    }
//    public void update_assignment(JSONObject updated) throws JSONException{
//        String assgnCode = updated.getString("id");
//        for(int i=0; i<assignment.length; ++i){
//            if(assignment[i].id.equals(assgnCode)){
//                assignment[i] = Utils.jsonObjectToAssignment(updated);
//            }
//        }
//    }
//    private LinkedHashMap[] grades;
//    private LinkedHashMap[] assignments;
//    private LinkedHashMap course_details;
//
//    public void flush(){
//        grades = null;
//        assignments = null;
//    };


}
