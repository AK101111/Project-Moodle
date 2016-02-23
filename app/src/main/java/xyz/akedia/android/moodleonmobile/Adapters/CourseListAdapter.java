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

import xyz.akedia.android.moodleonmobile.Course;
import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.R;

/**
 * Created by ashish on 15/2/16.
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>{

    private static final String TAG = CourseListAdapter.class.getSimpleName();

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView courseCode, courseName, courseDescription, courseCredits;

        CourseViewHolder(final View itemView, final Activity activity) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            courseCode = (TextView)itemView.findViewById(R.id.course_code);
            courseName = (TextView)itemView.findViewById(R.id.course_name);
            courseDescription = (TextView)itemView.findViewById(R.id.course_description);
            courseCredits = (TextView)itemView.findViewById(R.id.course_credits);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(activity,CourseDetailsActivity.class);
                    intent.putExtra("courseCode",courseCode.getText().toString());
                    activity.startActivity(intent);
                    //Toast.makeText(itemView.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    CourseList courseList;
    Activity parentActivity;

    public CourseListAdapter(CourseList list,Activity activity){
        this.courseList = list;
        this.parentActivity = activity;
    }

    public void updateCourseList(CourseList newCourseList) {
        this.courseList = newCourseList;
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG,"Adapter getItemCount = " + courseList.courseCount());
        if(courseList == null)
            return 0;
        return courseList.courseCount();
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_list, viewGroup, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(v,parentActivity);
        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        Course course = courseList.getCourse(i);
        courseViewHolder.courseCode.setText(course.courseCode.toUpperCase());
        courseViewHolder.courseName.setText(course.courseName);
        courseViewHolder.courseDescription.setText(course.courseDescription);
        courseViewHolder.courseCredits.setText(course.courseCredits);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}