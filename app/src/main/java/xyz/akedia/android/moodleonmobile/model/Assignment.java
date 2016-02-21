package xyz.akedia.android.moodleonmobile.model;

import java.util.LinkedHashMap;

/**
 * Created by arnavkansal on 20/02/16.
 */
/*
name: "Project Submission 1: Draft Requirement Document",
file_: null,
created_at: "2016-02-14 18:31:35",
registered_course_id: 1,
late_days_allowed: 0,
type_: 0,
deadline: "2016-02-21 18:31:35",
id: 1,
description: "<p><br></p><p>Organise 2 hr meeting of the team to</p>"
 */
public class Assignment {
    private String name;
    private String file_;
    private String created_at;
    private String registered_course_id;
    private String late_days_allowed;
    private String type_;
    private String deadline;
    public String id;
    private String description;

    public void set_init(LinkedHashMap<String, String> assignment){
        name = assignment.get("name");
        file_ = assignment.get("file_");
        created_at = assignment.get("created_at");
        registered_course_id = assignment.get("registered_course_id");
        late_days_allowed = assignment.get("late_days_allowed");
        type_ = assignment.get("type_");
        deadline = assignment.get("deadline");
        id = assignment.get("id");
        description = assignment.get("description");
    }
}
