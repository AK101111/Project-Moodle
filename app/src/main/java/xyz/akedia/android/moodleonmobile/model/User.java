package xyz.akedia.android.moodleonmobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by akedia on 14/02/16.
 */
public class User implements Serializable {
    private Courses[] courses;
    private int currSem;
    private int currYear;
    private String tab;
    private LinkedHashMap userDetails;

    public void User(){};

    public void flush(){
        courses = null;
        userDetails = null;
        currSem = 0;
        currYear = 0;
    };

    public void update_listCourses(JSONObject jsonObject) throws JSONException {
        userDetails = Utils.jsonObjectToHashMap(jsonObject.getJSONObject("user"));
        currSem = jsonObject.getInt("current_sem");
        currYear = jsonObject.getInt("current_year");
        tab = jsonObject.getString("tab");

        JSONArray coursesArray = jsonObject.getJSONArray("courses");
        courses = new Courses [coursesArray.length()];
        for(int i=0; i<coursesArray.length(); ++i){
            courses[i] = Utils.jsonObjectToCourseinfo(coursesArray.getJSONObject(i));
        }
    }
//
//    LinkedHashMap[] courses;
//    LinkedHashMap[] grades;
//    courses = Utils.jsonArrayToHashMap(jsonObject.getJSONArray("courses"));
//    grades = Utils.jsonArrayToHashMap(jsonObject.getJSONArray("grades"));
//    ArrayList<LinkedHashMap>[] Grades = mergeCoursesGrades(courses, grades, user.access_courses());
//    user.update_grades(Grades);
//
//    private ArrayList<LinkedHashMap>[] mergeCoursesGrades(LinkedHashMap[] courses, LinkedHashMap[] grades, LinkedHashMap[] reg_courses) {
//        // assert courses.length==grades.length: "Invalid response from server";
//        ArrayList<LinkedHashMap>[] Grades = (ArrayList<LinkedHashMap>[])new ArrayList[reg_courses.length];
//        int index;
//        for(int i=0; i<courses.length; ++i) {
//            index = Utils.findinCoursesArray(courses[i].get("code"), reg_courses);
//            // assert index>=0: "Invalid response of grades";
//            Grades[i].add(reg_courses[index]);
//        }
//        return Grades;
//    }
//
    public void update_gradeList(String coursecode, JSONObject jsonObject) throws JSONException {
        //TODO
        //  ADD
        //  tab
        // year
        //sem
        //resources
        //previous
        //assignments
        //registered
        //course_threads: [ ],
        JSONArray grades1 = jsonObject.getJSONArray("grades");
        Grades[] grades = new Grades[grades1.length()];
        for(int i=0;i<grades1.length();++i){
            grades[i] = Utils.jsonObjectToGrades(grades1.getJSONObject(i));
        }
        for(int i=0;i<courses.length; ++i){
            if(courses[i].code.equals(coursecode)){
                courses[i].set_grades(grades);
                break;
            }
        }
    }

    public void update_assignmentList(String courseCode, JSONObject jsonObject) throws JSONException {
        JSONArray assignment1 = jsonObject.getJSONArray("grades");
        Assignment[] assignment = new Assignment[assignment1.length()];
        for(int i=0;i<assignment1.length();++i){
            assignment[i] = Utils.jsonObjectToAssignment(assignment1.getJSONObject(i));
        }
        for(int i=0;i<courses.length; ++i){
            if(courses[i].code.equals(courseCode)){
                courses[i].set_assignment(assignment);
                break;
            }
        }
    }

    public void updateAllgrades(JSONObject jsonObject) throws JSONException{
        JSONArray courses = jsonObject.getJSONArray("courses");
        JSONArray grades = jsonObject.getJSONArray("grades");
        mergeAndupdategrades(courses, grades);
    }

    private void mergeAndupdategrades(JSONArray mergecourses, JSONArray mergegrades) throws JSONException {
        //assert mergecourses.length()==mergegrades.length() : Invalid Response
        for(int i=0; i<mergecourses.length();++i){
            String course_id = mergecourses.getJSONObject(i).getString("id");
            for(int j=0; j<courses.length; ++j){
                if(course_id == courses[i].id){
                    Grades[] updateGrade = courses[i].grades;
                    String match_id = mergegrades.getJSONObject(i).getString("id");
                    for(int k=0; k<updateGrade.length; ++k){
                        if(match_id==updateGrade[k].id) {
                            courses[i].grades[k].update(mergecourses.getJSONObject(i));
                        }
                    }
                }
            }
        }
    }

//    public void update_grades(ArrayList<LinkedHashMap>[] Grades) {
//        grades = Grades;
//    }
//
//
//
//    public void update_Assignments(ArrayList<LinkedHashMap>[] Assignments, LinkedHashMap[] Course_details) {
//        assignments = Assignments;
//        course_details = Course_details;
//    }

}
