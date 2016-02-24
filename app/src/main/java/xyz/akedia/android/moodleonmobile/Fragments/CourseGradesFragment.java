package xyz.akedia.android.moodleonmobile.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.Adapters.AssignmentListAdapter;
import xyz.akedia.android.moodleonmobile.Adapters.GradeListAdapter;
import xyz.akedia.android.moodleonmobile.Adapters.NotificationListAdapter;
import xyz.akedia.android.moodleonmobile.Assignment;
import xyz.akedia.android.moodleonmobile.AssignmentList;
import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.Grades;
import xyz.akedia.android.moodleonmobile.network.AssignmentFetcher;
import xyz.akedia.android.moodleonmobile.network.GradeFetcher;
import xyz.akedia.android.moodleonmobile.network.NotificationsFetcher;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseGradesFragment extends Fragment {
    RecyclerView recyclerView;
    List<Grades> gradeList;
    TextView notice;
    SwipeRefreshLayout swipeRefreshLayout;
    GradeListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_grades_fragment,container,false);
        init(v);
        return v;
    }
    private void init(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.gradeList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        notice = (TextView)view.findViewById(R.id.no_grades_layout);

        adapter = new GradeListAdapter(gradeList,getActivity());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        notice.setText("Loading grades...");
        notice.setVisibility(View.VISIBLE);
        refresh();
    }
    private void refresh(){
        String courseCode = ((CourseDetailsActivity)getActivity()).courseCode;
        String url = MoodleOnMobile.App.getMoodleUrl() + ApiUrls.COURSE_THREADS_BASE
                + courseCode +"/grades";
        GradeFetcher gradeFetcher = new GradeFetcher(getResponseHandler(),url);
        swipeRefreshLayout.setRefreshing(true);
        gradeFetcher.getGradeFetcher();
    }
    private GradeFetcher.GradeResponseHandler getResponseHandler(){
        final GradeFetcher.GradeResponseHandler responseHandler = new GradeFetcher.GradeResponseHandler() {
            @Override
            public void onSuccess(JSONObject grades) {
                Log.d("Grades", grades.toString());
                try {
                    JSONArray gradesData = grades.getJSONArray("grades");
                    gradeList = new ArrayList<>();
                    for (int i = 0; i < gradesData.length(); i++) {
                        JSONObject gradeData = (JSONObject) gradesData.get(i);
                        Grades grade = new Grades(gradeData.getString("weightage"),
                                gradeData.getString("name"),
                                gradeData.getString("score"),
                                gradeData.getString("out_of"));
                        gradeList.add(grade);
                    }

                    if (gradesData.length() > 0) {
                        adapter = new GradeListAdapter(gradeList, getActivity());
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setVisibility(View.VISIBLE);
                        notice.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        notice.setText("No grades available right now.");
                        notice.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                notice.setText("Can't connect to the internet");
                notice.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                notice.setText("Can't connect to the internet");
                notice.setVisibility(View.VISIBLE);
            }
        };
        return responseHandler;
    }
}
