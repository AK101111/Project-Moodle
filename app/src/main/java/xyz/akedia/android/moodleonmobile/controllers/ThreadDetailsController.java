package xyz.akedia.android.moodleonmobile.controllers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.model.*;
import xyz.akedia.android.moodleonmobile.model.Thread;
import xyz.akedia.android.moodleonmobile.network.ThreadInfoFetcher;
import xyz.akedia.android.moodleonmobile.utils.ParseResponse;

/**
 * Created by akedia on 24/02/16.
 */
public class ThreadDetailsController {
    private static final String TAG = ThreadDetailsController.class.getSimpleName();
    public interface SyncResponseHandler {
        void onSyncWait();
        void finishSyncWait();
        void onUpdate(ArrayList<Comment> updatedComments);
    }
    public interface AsyncResponseHandler {
        void onResponse(ArrayList<Comment> commentList);
        void duringWait();
        void finishWait();
    }

    public static ArrayList<Comment> getCommentListSync(String courseCode, int threadId, final SyncResponseHandler handler) {
        try {
            Course course = MoodleOnMobile.getUser().findCourse(courseCode);
            final Thread thread = course.findThread(threadId);
            ArrayList<Comment> commentList = thread.comments;
            if(commentList == null) {
                new ThreadInfoFetcher(threadId,new ThreadInfoFetcher.ThreadInfoResponseHandler(){
                    @Override
                    public void onSuccess(JSONObject courseData, JSONObject threadData, JSONArray comments, JSONArray updateTime, JSONArray commentUsers) {
                        try {
                            ArrayList<Comment> newComments = ParseResponse.parseComments(comments,updateTime,commentUsers);
                            thread.setComments(newComments);
                            handler.onUpdate(newComments);
                            handler.finishSyncWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler.finishSyncWait();
                        }
                    }
                    @Override
                    public void onFailure() {
                        handler.finishSyncWait();
                        Log.d(TAG,"failed!");
                    }
                    @Override
                    public void onError(Exception ex){
                        handler.finishSyncWait();
                        ex.printStackTrace();
                    }
                }).getThreadInfo();
                handler.onSyncWait();
                return new ArrayList<>();
            } else {
                new ThreadInfoFetcher(threadId, new ThreadInfoFetcher.ThreadInfoResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject courseData, JSONObject threadData, JSONArray comments, JSONArray updateTime, JSONArray commentUsers) {
                        try {
                            ArrayList<Comment> newComments = ParseResponse.parseComments(comments,updateTime,commentUsers);
                            thread.setComments(newComments);
                            handler.onUpdate(newComments);
                            handler.finishSyncWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler.finishSyncWait();
                        }
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG,"failed");
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }).getThreadInfo();
                return commentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void getCommentListAsync(String courseCode, int threadId,final AsyncResponseHandler handler) {
        Course course = MoodleOnMobile.getUser().findCourse(courseCode);
        final Thread thread = course.findThread(threadId);
        handler.duringWait();
        new ThreadInfoFetcher(threadId, new ThreadInfoFetcher.ThreadInfoResponseHandler() {
            @Override
            public void onSuccess(JSONObject courseData, JSONObject threadData, JSONArray comments, JSONArray updateTime, JSONArray commentUsers) {
                try {
                    ArrayList<Comment> newComments = ParseResponse.parseComments(comments,updateTime,commentUsers);
                    thread.setComments(newComments);
                    handler.onResponse(newComments);
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
        }).getThreadInfo();
    }
}
