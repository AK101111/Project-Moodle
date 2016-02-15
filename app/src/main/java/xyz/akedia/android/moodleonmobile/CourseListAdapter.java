package xyz.akedia.android.moodleonmobile;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ashish on 15/2/16.
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>{

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView courseName;

        CourseViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            courseName = (TextView)itemView.findViewById(R.id.course_name);
        }
    }
    List<String> courseNames;

    public CourseListAdapter(List<String> persons){
        this.courseNames = persons;
    }
    @Override
    public int getItemCount() {
        return courseNames.size();
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_list, viewGroup, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(v);
        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder personViewHolder, int i) {
        personViewHolder.courseName.setText(courseNames.get(i));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}