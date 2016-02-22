package xyz.akedia.android.moodleonmobile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 19/2/16.
 */
public class CourseList {

    private List<Course> courseList;

    public CourseList(){
        courseList = new ArrayList<>();
    }

    public void addCourse(Course course){
        courseList.add(course);
    }
    public Course getCourse(int position){
        return courseList.get(position);
    }
    public int courseCount(){
        return courseList.size();
    }
}
