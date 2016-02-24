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

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.Adapters.CourseThreadAdapter;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.controllers.ThreadListController;
import xyz.akedia.android.moodleonmobile.model.Thread;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseThreadsFragment extends Fragment {
    private final static String TAG = CourseThreadsFragment.class.getSimpleName();
    ArrayList<Thread> threadList;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_course_thread_fragment,container,false);
        init(v);
        return v;
    }
    public void setVals(ArrayList<Thread> list){
        this.threadList = list;
    }

    private void init(View view){
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.threadList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        final CourseThreadAdapter adapter = new CourseThreadAdapter(threadList,getActivity());
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        ArrayList<Thread> initialThreadList = ThreadListController.getThreadListSynchronously(new ThreadListController.SyncResponseHandler1(){
            @Override
            public void onSyncWait(){
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
            }
            @Override
            public void finishSyncWait(){
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onUpdate(ArrayList<Thread> updatedThreadList){
                adapter.updateThreadList(updatedThreadList);
                recyclerView.setAdapter(adapter);
            };
        });
//        adapter.updateThreadList(initialThreadList);
//        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //after refresh complete set this
//                swipeRefreshLayout.setRefreshing(false);
                ThreadListController.getThreadListAsync(new ThreadListController.AsyncResponseHandler1() {
                    @Override
                    public void onResponse(final ArrayList<Thread> newThreadList) {
                        adapter.updateThreadList(newThreadList);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void duringWait() {

                    }

                    @Override
                    public void finishWait() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}