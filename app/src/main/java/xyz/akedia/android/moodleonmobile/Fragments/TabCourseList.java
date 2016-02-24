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

import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.Adapters.CourseListAdapter;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.controllers.AsyncResponseHandler;
import xyz.akedia.android.moodleonmobile.controllers.CourseListController;
import xyz.akedia.android.moodleonmobile.controllers.SyncResponseHandler;

/**
 * Created by ashish on 15/2/16.
 */
public class TabCourseList extends Fragment {
    private final static String TAG = TabCourseList.class.getSimpleName();
    CourseList courseList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView notice;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_course_list_fragment,container,false);
        init(v);
        return v;
    }
    public void setVals(CourseList list){
        this.courseList = list;
    }
    private void init(final View view){
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.courseList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        final CourseListAdapter adapter = new CourseListAdapter(null,getActivity());
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        notice = (TextView)view.findViewById(R.id.no_course_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);

        CourseList initialCourseList = CourseListController.getCourseListSynchronously(new SyncResponseHandler() {
            @Override
            public void onSyncWait() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        notice.setText("Loading courses...");
                        notice.setVisibility(View.VISIBLE);
//                        swipeRefreshLayout.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
            }

            @Override
            public void finishSyncWait() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onUpdate(CourseList updatedCourseList) {
                if(updatedCourseList.courseCount() > 0) {
                    adapter.updateCourseList(updatedCourseList);
                    recyclerView.setAdapter(adapter);
                    notice.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    notice.setVisibility(View.VISIBLE);
                    notice.setText("No courses to view");
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });
        adapter.updateCourseList(initialCourseList);
        recyclerView.setAdapter(adapter);

//        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //after refresh complete set this
//                swipeRefreshLayout.setRefreshing(false);
                CourseListController.getCourseListAsync(new AsyncResponseHandler() {
                    @Override
                    public void onResponse(final CourseList newCourseList) {
//                        swipeRefreshLayout.setRefreshing(true);
//                        CourseListAdapter adapter = new CourseListAdapter(newCourseList,getActivity());
                        if(newCourseList.courseCount() > 0) {
                            adapter.updateCourseList(newCourseList);
                            recyclerView.setAdapter(adapter);
                            notice.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            notice.setVisibility(View.VISIBLE);
                            notice.setText("No courses to view");
                            recyclerView.setVisibility(View.GONE);
                        }
//                        swipeRefreshLayout.setRefreshing(false);
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
