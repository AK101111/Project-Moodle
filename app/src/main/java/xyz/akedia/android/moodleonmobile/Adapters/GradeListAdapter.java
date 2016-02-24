package xyz.akedia.android.moodleonmobile.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.model.Grades;
import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by ashish on 24/2/16.
 */
public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.GradeViewHolder>{

    private static final String TAG = CourseListAdapter.class.getSimpleName();

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView gradeName,score, weightage,courseCode;

        GradeViewHolder(final View itemView, final Activity activity) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            gradeName = (TextView)itemView.findViewById(R.id.grade_name);
            score = (TextView)itemView.findViewById(R.id.score);
            weightage = (TextView)itemView.findViewById(R.id.weightage);
            courseCode = (TextView)itemView.findViewById(R.id.course_code);
        }
    }
    List<Grades> gradeList;
    Activity parentActivity;

    public GradeListAdapter(List<Grades> list,Activity activity){
        this.gradeList = list;
        this.parentActivity = activity;
    }

    public void updateCourseList(List<Grades> newCourseList) {
        this.gradeList = newCourseList;
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG,"Adapter getItemCount = " + courseList.courseCount());
        if(gradeList == null)
            return 0;
        return gradeList.size();
    }
    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grade, viewGroup, false);
        GradeViewHolder gradeViewHolder = new GradeViewHolder(v,parentActivity);
        return gradeViewHolder;
    }
    @Override
    public void onBindViewHolder(GradeViewHolder courseViewHolder, int i) {
        Grades grade = gradeList.get(i);
        courseViewHolder.gradeName.setText(Html.fromHtml(grade.name));
        courseViewHolder.score.setText(Html.fromHtml("Score : "+grade.score));
        courseViewHolder.weightage.setText(Html.fromHtml("Weightage : "+grade.weightage));
        if(grade.courseCode != null) {
            courseViewHolder.courseCode.setText(grade.courseCode.toUpperCase() + " -");
            courseViewHolder.courseCode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}