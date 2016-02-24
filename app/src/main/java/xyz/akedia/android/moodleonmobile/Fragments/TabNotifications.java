package xyz.akedia.android.moodleonmobile.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import xyz.akedia.android.moodleonmobile.Adapters.CourseListAdapter;
import xyz.akedia.android.moodleonmobile.Adapters.NotificationListAdapter;
import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.NotificationList;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.controllers.AsyncResponseHandler;
import xyz.akedia.android.moodleonmobile.controllers.CourseListController;
import xyz.akedia.android.moodleonmobile.controllers.SyncResponseHandler;
import xyz.akedia.android.moodleonmobile.model.Notification;
import xyz.akedia.android.moodleonmobile.network.NotificationsFetcher;

/**
 * Created by ashish on 15/2/16.
 */
public class TabNotifications extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    NotificationList notificationList;
    NotificationListAdapter adapter;
    RecyclerView recyclerView;
    View parentView;
    TextView notice;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_notifications_fragment,container,false);
        parentView = v;
        init(v);
        return v;
    }
    public void setVals(NotificationList list){
        this.notificationList = list;
    }
    private void init(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.notificationList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        notice = (TextView)parentView.findViewById(R.id.no_notification_layout);

        adapter = new NotificationListAdapter(notificationList,getActivity());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        notice.setText("Loading notifications...");
        notice.setVisibility(View.VISIBLE);
        refresh();
    }
    private void refresh(){
        NotificationsFetcher notificationsFetcher = new NotificationsFetcher(getResponseHandler());
        swipeRefreshLayout.setRefreshing(true);
        notificationsFetcher.getNotifications();
    }
    private NotificationsFetcher.NotificationsResponseHandler getResponseHandler(){
        NotificationsFetcher.NotificationsResponseHandler responseHandler = new NotificationsFetcher.NotificationsResponseHandler() {
            @Override
            public void onSuccess(JSONArray notifications) {
                Log.d("Notif",notifications.toString());
                try {
                    notificationList = new NotificationList();
                    for (int i = 0; i < notifications.length(); i++) {
                        JSONObject notificationData = notifications.getJSONObject(i);
                        Notification notification = new Notification(notificationData.getString("user_id"),
                                                                notificationData.getString("description"),
                                                                notificationData.getInt("is_seen"),
                                                                notificationData.getString("created_at"),
                                                                notificationData.getString("id"));
                        notificationList.addNotification(notification);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(notifications.length() > 0) {
                    adapter = new NotificationListAdapter(notificationList, getActivity());
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    notice.setVisibility(View.GONE);
                }else {
                    swipeRefreshLayout.setVisibility(View.GONE);
                    notice.setText("No notifications");
                    notice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setVisibility(View.GONE);
                notice.setText("Can't connect to the internet");
                notice.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setVisibility(View.GONE);
                notice.setText("Can't connect to the internet");
                notice.setVisibility(View.VISIBLE);
            }
        };
        return responseHandler;
    }
}
