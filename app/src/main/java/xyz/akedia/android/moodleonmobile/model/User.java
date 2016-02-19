package xyz.akedia.android.moodleonmobile.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by akedia on 14/02/16.
 */
public class User implements Serializable {
    private LinkedHashMap[] courses;
    private int currSem;
    private int currYear;
    private LinkedHashMap userDetails;
    private ArrayList<LinkedHashMap>[] grades;
    public void User(){};

    public void update_Info(int currentSem, LinkedHashMap[] currentCourses, LinkedHashMap Info, int currentYear){
        courses = currentCourses;
        currSem = currentSem;
        currYear = currentYear;
        userDetails = Info;
    }

    public void flush(){
        courses = null;
        userDetails = null;
        currSem = 0;
        currYear = 0;
    };

    public void update_grades(ArrayList<LinkedHashMap>[] Grades) {
        grades = Grades;
    }

    public LinkedHashMap[] access_courses() {
        return courses;
    }
}
