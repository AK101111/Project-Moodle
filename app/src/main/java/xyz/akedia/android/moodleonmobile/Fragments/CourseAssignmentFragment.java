package xyz.akedia.android.moodleonmobile.Fragments;

import android.nfc.Tag;
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

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.Adapters.AssignmentListAdapter;
import xyz.akedia.android.moodleonmobile.Adapters.NotificationListAdapter;
import xyz.akedia.android.moodleonmobile.Assignment;
import xyz.akedia.android.moodleonmobile.AssignmentList;
import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.NotificationList;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.network.AssignmentFetcher;
import xyz.akedia.android.moodleonmobile.network.NotificationsFetcher;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseAssignmentFragment extends Fragment{
    SwipeRefreshLayout swipeRefreshLayout;
    AssignmentList assignmentList;
    AssignmentListAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_assignment_fragment,container,false);
        init(v);
        return v;
    }
    public void setVals(AssignmentList list){
        this.assignmentList = list;
    }
    private void init(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.assignmentList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        adapter = new AssignmentListAdapter(assignmentList,getActivity());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        refresh();
    }
    private void refresh(){
        String courseCode = ((CourseDetailsActivity)getActivity()).courseCode;
        AssignmentFetcher assignmentFetcher = new AssignmentFetcher(getResponseHandler(),courseCode);
        swipeRefreshLayout.setRefreshing(true);
        assignmentFetcher.getAssignments();
    }
    private AssignmentFetcher.AssignmentResponseHandler getResponseHandler(){
        AssignmentFetcher.AssignmentResponseHandler responseHandler = new AssignmentFetcher.AssignmentResponseHandler() {
            @Override
            public void onSuccess(JSONArray assignments) {
                Log.d("Assignments", assignments.toString());
                try {
                    assignmentList = new AssignmentList();
                    for (int i = 0; i < assignments.length(); i++) {
                        JSONObject assignmentData = (JSONObject) assignments.get(i);
                        Assignment assignment = new Assignment(assignmentData.getString("name"),
                                                                assignmentData.getString("created_at"),
                                                                assignmentData.getString("deadline"));
                        assignmentList.addAssignment(assignment);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                adapter = new AssignmentListAdapter(assignmentList,getActivity());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Exception e) {
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        return responseHandler;
    }
}
