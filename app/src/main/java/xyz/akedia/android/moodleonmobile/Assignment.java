package xyz.akedia.android.moodleonmobile;

import java.io.Serializable;

/**
 * Created by ashish on 24/2/16.
 */
public class Assignment implements Serializable{
    public String assignmentName;
    public String dateCreated;
    public String deadLine;
    public String lateDaysAllowed;
    public String description;
//    private String posted_by;
//    private String thread_link;
//    private String courseid;

    public Assignment(String name, String date,String deadline,String lateDays,String desc){
        this.assignmentName = name;
        this.dateCreated = date;
        this.deadLine = deadline;
        this.lateDaysAllowed = lateDays;
        this.description = desc;
    }
}
