package xyz.akedia.android.moodleonmobile.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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
public class Courses {
    public String code;
    private String name;
    private String description;
    private String credits;
    public String id;
    private String credits_scheme;
    public Grades[] grades;
    public Assignment[] assignment;
    private courseResources[] resources;

    public void set_init(LinkedHashMap<String, String> courseDetails) {
        code = courseDetails.get("code");
        name = courseDetails.get("name");
        description = courseDetails.get("description");
        credits = courseDetails.get("credits");
        id = courseDetails.get("id");
        credits_scheme = courseDetails.get("credits_scheme");
    }

    public void set_grades(Grades[] gradesList){
        grades = gradesList;
    }

    public void set_assignment(Assignment[] assignmentList){
        assignment = assignmentList;
    }

//    private LinkedHashMap[] grades;
//    private LinkedHashMap[] assignments;
//    private LinkedHashMap course_details;
//
//    public void flush(){
//        grades = null;
//        assignments = null;
//    };


}
