package xyz.akedia.android.moodleonmobile;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ashish on 15/2/16.
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>{

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView courseCode, courseName, courseDescription, courseCredits;

        CourseViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            courseCode = (TextView)itemView.findViewById(R.id.course_code);
            courseName = (TextView)itemView.findViewById(R.id.course_name);
            courseDescription = (TextView)itemView.findViewById(R.id.course_description);
            courseCredits = (TextView)itemView.findViewById(R.id.course_credits);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    CourseList courseList;

    public CourseListAdapter(CourseList list){
        this.courseList = list;
    }
    @Override
    public int getItemCount() {
        return courseList.courseCount();
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_list, viewGroup, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(v);
        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        Course course = courseList.getCourse(i);
        courseViewHolder.courseCode.setText(course.courseCode);
        courseViewHolder.courseName.setText(course.courseName);
        courseViewHolder.courseDescription.setText(course.courseDescription);
        courseViewHolder.courseCredits.setText(course.courseCredits);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}