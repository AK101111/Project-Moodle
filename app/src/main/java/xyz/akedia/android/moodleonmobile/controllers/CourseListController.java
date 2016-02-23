package xyz.akedia.android.moodleonmobile.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.akedia.android.moodleonmobile.CourseList;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.model.Course;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.network.CourseListFetcher;
import xyz.akedia.android.moodleonmobile.utils.ParseResponse;

/**
 * Created by akedia on 23/02/16.
 */
public class CourseListController {
    public static final String TAG = CourseListController.class.getSimpleName();

    public static CourseList getCourseListSynchronously(final SyncResponseHandler handler) {
        User user = MoodleOnMobile.getUser();
        ArrayList<Course> courseList = user.getCourseList();
        if(courseList == null) {
            new CourseListFetcher(new CourseListFetcher.CourseListResponseHandler() {
                @Override
                public void onSuccess(int currentSem, JSONArray courseList, JSONObject user) {
                    try {
                        ArrayList<Course> updatedList = ParseResponse.parseCourseList(courseList);

                        MoodleOnMobile.getUser().setCourseList(updatedList);
                        MoodleOnMobile.getUser().setCurrentSem(currentSem);

                        handler.onUpdate(CourseList.fromModel(updatedList));
                        handler.finishSyncWait();
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.finishSyncWait();
                    }
                }

                @Override
                public void onFailure() {
                    handler.finishSyncWait();
                    Log.d(TAG,"Failed to fetch course list");
                }

                @Override
                public void onError(Exception e) {
                    handler.finishSyncWait();
                    e.printStackTrace();
                }
            }).getCoursesList();
            handler.onSyncWait();
            return new CourseList();
        } else {
            new CourseListFetcher(new CourseListFetcher.CourseListResponseHandler() {
                @Override
                public void onSuccess(int currentSem, JSONArray courseList, JSONObject user) {
                    try {
                        ArrayList<Course> updatedList = ParseResponse.parseCourseList(courseList);

                        MoodleOnMobile.getUser().setCourseList(updatedList);
                        MoodleOnMobile.getUser().setCurrentSem(currentSem);

                        handler.onUpdate(CourseList.fromModel(updatedList));
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
