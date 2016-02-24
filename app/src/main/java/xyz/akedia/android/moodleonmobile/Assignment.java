package xyz.akedia.android.moodleonmobile;

/**
 * Created by ashish on 24/2/16.
 */
public class Assignment {
    public String assignmentName;
    public String dateCreated;
    public String lateDaysAllowed;
//    private String posted_by;
//    private String thread_link;
//    private String courseid;

    public Assignment(String name, String date,String lateDays){
        this.assignmentName = name;
        this.dateCreated = date;
        this.lateDaysAllowed = lateDays;
    }
}
