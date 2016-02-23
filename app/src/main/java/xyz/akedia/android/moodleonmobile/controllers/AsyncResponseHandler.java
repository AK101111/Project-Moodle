package xyz.akedia.android.moodleonmobile.controllers;

import xyz.akedia.android.moodleonmobile.CourseList;

/**
 * Created by akedia on 23/02/16.
 */
public interface AsyncResponseHandler {
    void onResponse(CourseList courseList);
    void duringWait();
    void finishWait();
}
