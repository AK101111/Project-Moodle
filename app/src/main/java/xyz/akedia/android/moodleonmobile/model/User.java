package xyz.akedia.android.moodleonmobile.model;

import android.app.Notification;

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
    private int userId;
    private String firstName;
    private String lastName;
    private String entryNumber;
    private String username;
    private String email;
    private boolean isStudent;

    private int currentSem;
    private ArrayList<Course> courses;

//    private Courses[] courses;
    private int currSem;
    private int currYear;
    private String tab;
    private LinkedHashMap userDetails;
    private Notifications[] notifications;

//    public User() {};

    public User(int userId, String firstName, String lastName, String username, String entryNumber, String email, boolean isStudent) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.entryNumber = entryNumber;
        this.email = email;
        this.isStudent = isStudent;
    }

    public int getUserId() {
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEntryNumber() {
        return entryNumber;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public int getCurrentSem() {
        return currentSem;
    }


    //called when ever the user object is updated
    synchronized public void onUpdate() {
        //TODO
    }

    public void setCurrentSem(int currentSem) {
        this.currentSem = currentSem;
        onUpdate();
    }

    public void setCourseList(ArrayList<Course> courseList) {
        //improve
        this.courses = courseList;
        onUpdate();
    }

    public ArrayList<Course> getCourseList() {
        return courses;
    }

    public Course findCourse(String coursecode) {
        for(int i=0; i<courses.size();++i){
            if(courses.get(i).getCode()==coursecode) return courses.get(i);
        }
        return null;
    }

//    public void flush(){
//        courses = null;
//        userDetails = null;
//        currSem = 0;
//        currYear = 0;
//    };

//    public void update_listCourses(JSONObject jsonObject) throws JSONException {
//        userDetails = Utils.jsonObjectToHashMap(jsonObject.getJSONObject("user"));
//        currSem = jsonObject.getInt("current_sem");
//        currYear = jsonObject.getInt("current_year");
//        tab = jsonObject.getString("tab");
//
//        JSONArray coursesArray = jsonObject.getJSONArray("courses");
//        courses = new Courses [coursesArray.length()];
//        for(int i=0; i<coursesArray.length(); ++i){
//            courses[i] = Utils.jsonObjectToCourseinfo(coursesArray.getJSONObject(i));
//        }
//    }
//    public void update_gradeList(String coursecode, JSONObject jsonObject) throws JSONException {
//        //TODO
//        //  ADD
//        //  tab
//        // year
//        //sem
//        //resources
//        //previous
//        //assignments
//        //registered
//        //course_threads: [ ],
//        JSONArray grades1 = jsonObject.getJSONArray("grades");
//        Grades[] grades = new Grades[grades1.length()];
//        for(int i=0;i<grades1.length();++i){
//            grades[i] = Utils.jsonObjectToGrades(grades1.getJSONObject(i));
//        }
//        for(int i=0;i<courses.length; ++i){
//            if(courses[i].code.equals(coursecode)){
//                courses[i].set_grades(grades);
//                break;
//            }
//        }
//    }

//    public void update_assignmentList(String courseCode, JSONObject jsonObject) throws JSONException {
//        JSONArray assignment1 = jsonObject.getJSONArray("grades");
//        Assignment[] assignment = new Assignment[assignment1.length()];
//        for(int i=0;i<assignment1.length();++i){
//            assignment[i] = Utils.jsonObjectToAssignment(assignment1.getJSONObject(i));
//        }
//        for(int i=0;i<courses.length; ++i){
//            if(courses[i].code.equals(courseCode)){
//                courses[i].set_assignment(assignment);
//                break;
//            }
//        }
//    }

//    public void updateAllgrades(JSONObject jsonObject) throws JSONException{
//        JSONArray courses = jsonObject.getJSONArray("courses");
//        JSONArray grades = jsonObject.getJSONArray("grades");
//        mergeAndupdategrades(courses, grades);
//    }
//
//    private void mergeAndupdategrades(JSONArray mergecourses, JSONArray mergegrades) throws JSONException {
//        //assert mergecourses.length()==mergegrades.length() : Invalid Response
//        for(int i=0; i<mergecourses.length();++i){
//            String course_id = mergecourses.getJSONObject(i).getString("id");
//            for(int j=0; j<courses.length; ++j){
//                if(course_id == courses[i].id){
//                    Grades[] updateGrade = courses[i].grades;
//                    String match_id = mergegrades.getJSONObject(i).getString("id");
//                    for(int k=0; k<updateGrade.length; ++k){
//                        if(match_id==updateGrade[k].id) {
//                            courses[i].grades[k].update(mergecourses.getJSONObject(i));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public void updatefindAssignmentbyCode(JSONObject Assignment) throws JSONException{
//        String assgncode = Assignment.getString("id");
//        String course_id = Assignment.getString("registered_course_id");
//        for (int i=0;i<courses.length; ++i){
//            if(courses[i].id.equals(course_id)){
//                courses[i].update_assignment(Assignment);
//                break;
//            }
//        }
//
//    }
//    public void updateNotifications(Notifications[] updatedNotifications){
//        notifications = updatedNotifications;
//    }
}
