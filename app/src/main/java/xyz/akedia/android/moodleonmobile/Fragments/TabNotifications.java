package xyz.akedia.android.moodleonmobile.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.akedia.android.moodleonmobile.Adapters.CourseListAdapter;
import xyz.akedia.android.moodleonmobile.Adapters.NotificationListAdapter;
import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.NotificationList;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.controllers.AsyncResponseHandler;
import xyz.akedia.android.moodleonmobile.controllers.CourseListController;
import xyz.akedia.android.moodleonmobile.controllers.SyncResponseHandler;

/**
 * Created by ashish on 15/2/16.
 */
public class TabNotifications extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    NotificationList notificationList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_notifications_fragment,container,false);
        init(v);
        return v;
    }
    public void setVals(NotificationList list){
        this.notificationList = list;
    }
    private void init(View view){
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.notificationList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        final NotificationListAdapter adapter = new NotificationListAdapter(notificationList,getActivity());
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
