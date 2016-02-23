package xyz.akedia.android.moodleonmobile.controllers;

//import xyz.akedia.android.moodleonmobile.ThreadList;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.akedia.android.moodleonmobile.ThreadDetailsActivity;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.model.Thread;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.model.Course;
import xyz.akedia.android.moodleonmobile.network.ThreadListFetcher;
import xyz.akedia.android.moodleonmobile.utils.ParseResponse;

/**
 * Created by arnavkansal on 24/02/16.
 */
public class ThreadListController {
    public static final String TAG = ThreadListController.class.getSimpleName();
    //public static ThreadList

    public static Thread[] getThreadListSynchronously(final SyncResponseHandler handler) {
        User user = MoodleOnMobile.getUser();
        String coursecode = ThreadDetailsActivity.threadTitle;
        Course course = user.findCourse(coursecode);
        if(course==null){
            new ThreadListFetcher(new ThreadListFetcher.ThreadListResponseHandler() {
                @Override
                public void onSuccess(int currentSem, JSONArray threadList){
                    try {
                        ArrayList<Thread> updatedList = ParseResponse.parseThreadList(threadList);

                       // MoodleOnMobile.getUser().setCourseList(updatedList);
                       // MoodleOnMobile.getUser().setCurrentSem(currentSem);

                        //handler.onUpdate(CourseList.fromModel(updatedList));
                        //handler.finishSyncWait();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //handler.finishSyncWait();
                    }
                }

                @Override
                public void onFailure() {
                    //handler.finishSyncWait();
                    Log.d(TAG, "Failed to fetch course list");
                }

                @Override
                public void onError(Exception e) {
                    //handler.finishSyncWait();
                    e.printStackTrace();
                }
            }).getThreadList();
            //handler.onSyncWait();
            return new Thread[];
        } else {
            new ThreadListFetcher(new ThreadListFetcher().ThreadListResponseHandler() {
                @Override
                public void onSuccess(int currentSem, JSONArray threadList){
                    try {
                        ArrayList<Thread> updatedList = ParseResponse.parseThreadList(threadList);

                        //MoodleOnMobile.getUser().setCourseList(updatedList);
                        //MoodleOnMobile.getUser().setCurrentSem(currentSem);

                        //handler.onUpdate(CourseList.fromModel(updatedList));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {
                    Log.d(TAG,"Failed to update course list");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            }).getCoursesList();
            return CourseList.fromModel(courseList);
        }
    };

    public static void getCourseListAsync(final AsyncResponseHandler handler) {
        handler.duringWait();
        new CourseListFetcher(new CourseListFetcher.CourseListResponseHandler() {
            @Override
            public void onSuccess(int currentSem, JSONArray courseList, JSONObject user) {
                try {
                    Log.d(TAG,"got courseList : " + courseList.toString());
                    ArrayList<Course> updatedList = ParseResponse.parseCourseList(courseList);
                    Log.d(TAG,"array list size : " + updatedList.size());

                    MoodleOnMobile.getUser().setCourseList(updatedList);
                    MoodleOnMobile.getUser().setCurrentSem(currentSem);

                    handler.onResponse(CourseList.fromModel(updatedList));
                    handler.finishWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.finishWait();
                }
            }

            @Override
            public void onFailure() {
                Log.d(TAG,"Failed to update course list async");
                handler.finishWait();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                handler.finishWait();
            }
        }).getCoursesList();
    }
}