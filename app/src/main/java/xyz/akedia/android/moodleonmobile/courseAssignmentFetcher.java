package xyz.akedia.android.moodleonmobile;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import xyz.akedia.android.moodleonmobile.config.ApiUrls;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.utils.Utils;

/**
 * Created by arnavkansal on 20/02/16.
 */


/*
{
assignments: [
{
name: "Project Submission 1: Draft Requirement Document",
file_: null,
created_at: "2016-02-14 18:31:35",
registered_course_id: 1,
late_days_allowed: 0,
type_: 0,
deadline: "2016-02-21 18:31:35",
id: 1,
description: "<p><br></p><p>Organise 2 hr meeting of the team to</p><p>-Choose one of the Projects discussed in the class</p><p>-Discuss the specification of the selected project. Identify the aspects to be explored by team members&nbsp;</p><p>-Document the discussion and the initial specs of the project</p><p><br></p><p>Organise 2nd 2 hr meeting &nbsp;to</p><p>-Share the homework done by each team member</p><p>-Discuss and finalise the specs of the projects</p><p>-Prepare 1 or 2 page document on the draft project specification&nbsp;</p><p><br></p><p>Submit the draft Project Requirement Document by Wednesday mid night.</p><p>Add title of the project in the group excel sheet</p>"
},
{
name: "Project Submission 2: Requirement Document in IEEE template format",
file_: null,
created_at: "2016-02-14 18:31:35",
registered_course_id: 1,
late_days_allowed: 2,
type_: 0,
deadline: "2016-02-21 18:31:35",
id: 2,
description: "<p>Submission Deadline 20 Feb Midnight.</p><p id='yui_3_17_2_3_1431040674495_308'>Recommended Process</p><p>-Meeting1 &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Compete listing &nbsp;of User requirements</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Create System Architecture</p><p id='yui_3_17_2_3_1431040674495_309'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Identify Use cases, Users, draw Use Case Diagram<br></p><p>-Meeting2</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Translate user requirement into system requirements</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Discuss and document Use cases including relevant Models</p><p>-Meeting3&nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; Discuss each section of the IEEE template&nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; Create Document as per IEEE template</p><p><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>&nbsp;</p><p><br></p>"
},
{
name: "Project Submission 3 : Detailed Design Document",
file_: null,
created_at: "2016-02-14 18:31:35",
registered_course_id: 1,
late_days_allowed: 3,
type_: 0,
deadline: "2016-02-21 18:31:35",
id: 3,
description: "<p>Based on the Requirement Document, a detailed design document</p><p>will be created by each group.</p><p>It should have the following components</p><p>-Project Overview</p><p>-Architectural design with well identified Modules</p><p>-Modules specification &amp; its APIs</p><p>-Database Design</p><p>-User Interface Design</p><p>-Module internal data structures and processing if needed.</p><p>-Aprorpriate Diagrams as necessary</p><p>-Any other information as necessary</p><p>Design document should be complete from all aspects ( i.e Requirement &amp; design document should be adequate for any other programming team to develop the system without may further input)</p><p>You may use any apropriate format for this design document</p><p>Submission date 29th March.</p><p><br></p><p><br></p>"
}
],
registered: {
starting_date: "2016-01-01 00:00:00",
id: 1,
professor: 5,
semester: 2,
ending_date: "2016-05-10 00:00:00",
year_: 2016,
course_id: 1
},
course_threads: [ ],
course: {
code: "cop290",
name: "Design Practices in Computer Science",
description: "Design Practices in Computer Science.",
credits: 3,
id: 1,
l_t_p: "0-0-6"
},
grades: [ ],
tab: "assignments",
year: 2016,
sem: 2,
resources: [ ],
previous: [ ]
}
 */
public class courseAssignmentFetcher {
    private static final String TAG = courseAssignmentFetcher.class.getSimpleName();
    private RequestQueue courseAssignmentQueue;
    private User user;
    private String courseCode;
    public courseAssignmentFetcher(RequestQueue requestQueue, User userModel, String requestCourse){
        courseAssignmentQueue = requestQueue;
        user = userModel;
        courseCode = requestCourse;
    }
    public void getAssignmentList(String baseUrl){
        String requestUrl = baseUrl + ApiUrls.COURSE_BASE + courseCode + "/assignments";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            validateParseUpdateResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyError.
            }
        }
        );
        //Log.d()
        courseAssignmentQueue.add(jsonObjectRequest);
    }

    private void validateParseUpdateResponse(JSONObject jsonObject) throws JSONException {
        user.update_assignmentList(courseCode, jsonObject);
    }
}
