package xyz.akedia.android.moodleonmobile.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.ThreadDetailsActivity;
import xyz.akedia.android.moodleonmobile.model.Thread;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseThreadAdapter extends RecyclerView.Adapter<CourseThreadAdapter.CourseViewHolder>{
    public static final String TAG = CourseThreadAdapter.class.getSimpleName();

    public void updateThreadList(ArrayList<Thread> updatedThreadList) {
        this.threadList = updatedThreadList;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView threadTitle, threadSummary, threadDate;
        int threadId;

        CourseViewHolder(final View itemView, final Activity activity) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            threadTitle = (TextView)itemView.findViewById(R.id.thread_title);
            threadSummary = (TextView)itemView.findViewById(R.id.thread_description);
            threadDate = (TextView)itemView.findViewById(R.id.thread_date);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.d(TAG,String.format("courseCode:%s, threadId:%d", CourseDetailsActivity.courseCode.toLowerCase(),threadId));
                    Intent intent = new Intent(activity,ThreadDetailsActivity.class);
                    intent.putExtra("courseCode", CourseDetailsActivity.courseCode.toLowerCase() );
                    intent.putExtra("threadId", threadId);
                    activity.startActivity(intent);
                    //Toast.makeText(itemView.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    ArrayList<Thread> threadList;
    Activity parentActivity;

    public CourseThreadAdapter(ArrayList<Thread> list,Activity activity){
        this.threadList = list;
        this.parentActivity = activity;
    }
    @Override
    public int getItemCount() {
        if(threadList==null){
            return 0;
        }
        return threadList.size();
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_thread, viewGroup, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(v,parentActivity);
        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        Thread thread = threadList.get(i);
        courseViewHolder.threadTitle.setText(thread.getTitle());
        courseViewHolder.threadSummary.setText(thread.getDescription());
        courseViewHolder.threadDate.setText(thread.getCreatedAt());
        courseViewHolder.threadId = thread.getThreadId();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}