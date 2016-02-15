package xyz.akedia.android.moodleonmobile.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.akedia.android.moodleonmobile.CourseListAdapter;
import xyz.akedia.android.moodleonmobile.R;

/**
 * Created by ashish on 15/2/16.
 */
public class TabCourseList extends Fragment {
    List<String> courseNames;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_course_list_fragment,container,false);
        init(v);
        return v;
    }
    private void setVals(List<String> cNames){
        this.courseNames = cNames;
    }
    private void init(View view){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.courseList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        CourseListAdapter adapter = new CourseListAdapter(courseNames);
        recyclerView.setAdapter(adapter);
    }
}
