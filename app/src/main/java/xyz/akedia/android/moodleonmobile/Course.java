package xyz.akedia.android.moodleonmobile;

/**
 * Created by ashish on 19/2/16.
 */
public class Course {
    public String courseCode;
    public String courseName;
    public String courseDescription;
    public String courseCredits;

    public Course(String code,String name,String description, String credits){
        this.courseCode = code;
        this.courseName = name;
        this.courseDescription = description;
        this.courseCredits = credits;
    }

    public static Course fromModel(xyz.akedia.android.moodleonmobile.model.Course modelCourse) {
        return new Course(modelCourse.getCode(),modelCourse.getName(),modelCourse.getDescription(),modelCourse.getCourseScheme().toString());
    }
}
