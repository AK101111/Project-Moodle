package xyz.akedia.android.moodleonmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import xyz.akedia.android.moodleonmobile.utils.Utils;

public class AssignmentDetailsActivity extends AppCompatActivity {

    String assignmentTitle,assignmentDescription, dateCreated, deadline, lateDaysAllowed;
    Assignment assignment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Assignment details");
        setSupportActionBar(toolbar);
        init();
    }
    private void init(){
        assignment = (Assignment)getIntent().getSerializableExtra("assignmentData");
        assignmentTitle = assignment.assignmentName;
        assignmentDescription = assignment.description;
        dateCreated = assignment.dateCreated;
        deadline = assignment.deadLine;
        lateDaysAllowed = assignment.lateDaysAllowed;
        setData();
    }
    private void setData(){
        ((TextView)findViewById(R.id.assignment_name)).setText(assignmentTitle);
        ((TextView)findViewById(R.id.assignment_description)).setText(Html.fromHtml(assignmentDescription));
        ((TextView)findViewById(R.id.date_created)).setText("Created : "+Utils.parseDate(dateCreated));
        ((TextView)findViewById(R.id.deadline)).setText("Deadline : "+Utils.parseDate(deadline));
        ((TextView)findViewById(R.id.late_days_allowed)).setText("Late days allowed : "+lateDaysAllowed);
    }

}
