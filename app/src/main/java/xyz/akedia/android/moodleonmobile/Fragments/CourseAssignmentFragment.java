package xyz.akedia.android.moodleonmobile.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.akedia.android.moodleonmobile.Adapters.AssignmentListAdapter;
import xyz.akedia.android.moodleonmobile.Adapters.NotificationListAdapter;
import xyz.akedia.android.moodleonmobile.AssignmentList;
import xyz.akedia.android.moodleonmobile.NotificationList;
import xyz.akedia.android.moodleonmobile.R;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseAssignmentFragment extends Fragment{
    SwipeRefreshLayout swipeRefreshLayout;
    AssignmentList assignmentList;
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
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.assignmentList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        AssignmentListAdapter adapter = new AssignmentListAdapter(assignmentList,getActivity());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
}
