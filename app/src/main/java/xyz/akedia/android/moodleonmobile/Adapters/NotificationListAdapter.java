package xyz.akedia.android.moodleonmobile.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.akedia.android.moodleonmobile.Course;
import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.NotificationList;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.model.Notification;

/**
 * Created by ashish on 24/2/16.
 */
public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder>{

    private static final String TAG = CourseListAdapter.class.getSimpleName();

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView description, date;

        NotificationViewHolder(final View itemView, final Activity activity) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            description = (TextView)itemView.findViewById(R.id.notification_description);
            date = (TextView)itemView.findViewById(R.id.notification_date);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //Toast.makeText(itemView.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    NotificationList notificationList;
    Activity parentActivity;

    public NotificationListAdapter(NotificationList list,Activity activity){
        this.notificationList = list;
        this.parentActivity = activity;
    }

    public void updateCourseList(NotificationList newCourseList) {
        this.notificationList = newCourseList;
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG,"Adapter getItemCount = " + courseList.courseCount());
        if(notificationList == null)
            return 0;
        return notificationList.notificationCount();
    }
    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_list, viewGroup, false);
        NotificationViewHolder courseViewHolder = new NotificationViewHolder(v,parentActivity);
        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(NotificationViewHolder courseViewHolder, int i) {
        Notification notification = notificationList.getNotificationAt(i);
        courseViewHolder.description.setText(notification.description);
        courseViewHolder.date.setText(notification.createdAt);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}