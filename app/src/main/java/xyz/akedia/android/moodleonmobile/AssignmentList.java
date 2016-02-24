package xyz.akedia.android.moodleonmobile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.model.Notification;

/**
 * Created by ashish on 24/2/16.
 */
public class AssignmentList implements Serializable {
    private List<Assignment> assignmentList;

    public AssignmentList(){
        assignmentList = new ArrayList<>();
    }
    public void addAssignment(Assignment assignment){
        this.assignmentList.add(assignment);
    }
    public Assignment getAssignmentAt(int position){
        return this.assignmentList.get(position);
    }
    public int assignmentCount(){
        return assignmentList.size();
    }
}
