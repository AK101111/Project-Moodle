package xyz.akedia.android.moodleonmobile.controllers;

//import xyz.akedia.android.moodleonmobile.ThreadList;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.akedia.android.moodleonmobile.CourseDetailsActivity;
import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.ThreadDetailsActivity;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.model.Thread;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.model.Course;
import xyz.akedia.android.moodleonmobile.network.CourseListFetcher;
import xyz.akedia.android.moodleonmobile.network.ThreadListFetcher;
import xyz.akedia.android.moodleonmobile.utils.ParseResponse;

/**
 * Created by arnavkansal on 24/02/16.
 */
public class ThreadListController {
    public static final String TAG = ThreadListController.class.getSimpleName();
    //public static ThreadList

    public interface SyncResponseHandler1 {
        void onSyncWait();
        void finishSyncWait();
        void onUpdate(ArrayList<Thread> updatedThreadList);
    }
    public static ArrayList<Thread> getThreadListSynchronously(final SyncResponseHandler1 handler) {
        User user = MoodleOnMobile.getUser();
        final String coursecode = CourseDetailsActivity.courseCode;
        Log.d(TAG,"--> course code : " + coursecode);
        Course course = user.findCourse(coursecode);
        Log.d(TAG,"course : " + ((course==null)?"null":"notNull"));
        ArrayList<Thread> threadArrayList = course.threads;
        if(threadArrayList==null){
            new ThreadListFetcher(new ThreadListFetcher.ThreadListResponseHandler() {
                @Override
                public void onSuccess(int currentSem, JSONArray threadList){
                    try {
                        ArrayList<Thread> updatedList = ParseResponse.parseThreadList(threadList);

                       MoodleOnMobile.getUser().findCourse(coursecode).set_thread(updatedList);
                       MoodleOnMobile.getUser().setCurrentSem(currentSem);

                        handler.onUpdate(updatedList);
                        handler.finishSyncWait();
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.finishSyncWait();
                    }
                }

                @Override
                public void onFailure() {
                    handler.finishSyncWait();
                    Log.d(TAG, "Failed to fetch thread list");
                }

                @Override
                public void onError(Exception e) {
                    handler.finishSyncWait();
                    e.printStackTrace();
                }
            }).getThreadList();
            handler.onSyncWait();
            return new ArrayList<Thread>();
        } else {
            new ThreadListFetcher(new ThreadListFetcher.ThreadListResponseHandler() {
                @Override
                public void onSuccess(int currentSem, JSONArray threadList){
                    try {
                        ArrayList<Thread> updatedList = ParseResponse.parseThreadList(threadList);

                        MoodleOnMobile.getUser().findCourse(coursecode).set_thread(updatedList);
                        MoodleOnMobile.getUser().setCurrentSem(currentSem);
                        handler.onUpdate(updatedList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {
                    Log.d(TAG,"Failed to update thread list");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            }).getThreadList();
            return threadArrayList;
        }
    };
    public interface AsyncResponseHandler1 {
        void onResponse(ArrayList<Thread> threadList);
        void duringWait();
        void finishWait();
    }

    public static void getThreadListAsync(final AsyncResponseHandler1 handler) {
        final String coursecode = ThreadDetailsActivity.threadTitle;
        handler.duringWait();
        new ThreadListFetcher(new ThreadListFetcher.ThreadListResponseHandler() {
            @Override
            public void onSuccess(int currentSem, JSONArray threadList){
                try {
                    Log.d(TAG, "got threadList : " + threadList.toString());
                    ArrayList<Thread> updatedList = ParseResponse.parseThreadList(threadList);
                    Log.d(TAG, "array list size : " + updatedList.size());

                    MoodleOnMobile.getUser().findCourse(coursecode).set_thread(updatedList);
                    MoodleOnMobile.getUser().setCurrentSem(currentSem);

                    handler.onResponse(updatedList);
                    handler.finishWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.finishWait();
                }
            }

            @Override
            public void onFailure() {
                Log.d(TAG,"Failed to update thread list async");
                handler.finishWait();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                handler.finishWait();
            }
        }).getThreadList();
    }
}