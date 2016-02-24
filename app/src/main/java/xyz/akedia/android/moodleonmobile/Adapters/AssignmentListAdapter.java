package xyz.akedia.android.moodleonmobile.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.akedia.android.moodleonmobile.Assignment;
import xyz.akedia.android.moodleonmobile.AssignmentList;
import xyz.akedia.android.moodleonmobile.NotificationList;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.model.Notification;

/**
 * Created by ashish on 24/2/16.
 */
public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.AssignmentViewHolder>{

    private static final String TAG = AssignmentListAdapter.class.getSimpleName();

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView assignmentName, dateCreated, lateDaysAllowed;

        AssignmentViewHolder(final View itemView, final Activity activity) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            assignmentName = (TextView)itemView.findViewById(R.id.assignment_name);
            dateCreated = (TextView)itemView.findViewById(R.id.date);
            lateDaysAllowed = (TextView)itemView.findViewById(R.id.late_days_allowed);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //Toast.makeText(itemView.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    AssignmentList assignmentList;
    Activity parentActivity;

    public AssignmentListAdapter(AssignmentList list, Activity activity){
        this.assignmentList = list;
        this.parentActivity = activity;
    }

    public void updateAssignmentList(AssignmentList newAssignmentList) {
        this.assignmentList = newAssignmentList;
    }

    @Override
    public int getItemCount() {
        if(assignmentList == null)
            return 0;
        return assignmentList.assignmentCount();
    }
    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_assignment_list, viewGroup, false);
        AssignmentViewHolder assignmentViewHolder = new AssignmentViewHolder(v,parentActivity);
        return assignmentViewHolder;
    }
    @Override
    public void onBindViewHolder(AssignmentViewHolder assignmentViewHolder, int i) {
        Assignment assignment = assignmentList.getAssignmentAt(i);
        assignmentViewHolder.assignmentName.setText(assignment.assignmentName);
        assignmentViewHolder.dateCreated.setText(assignment.dateCreated);
        assignmentViewHolder.lateDaysAllowed.setText("Deadline : "+assignment.deadLine);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}