package xyz.akedia.android.moodleonmobile.controllers;

import xyz.akedia.android.moodleonmobile.CourseList;

/**
 * Created by akedia on 23/02/16.
 */
public interface SyncResponseHandler {
    void onSyncWait();
    void finishSyncWait();
    void onUpdate(CourseList updatedCourseList);
}
