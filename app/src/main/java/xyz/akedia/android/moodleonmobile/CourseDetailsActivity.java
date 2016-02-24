package xyz.akedia.android.moodleonmobile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import xyz.akedia.android.moodleonmobile.Adapters.CourseDetailsAdapter;
import xyz.akedia.android.moodleonmobile.controllers.ThreadListController;
import xyz.akedia.android.moodleonmobile.model.*;
import xyz.akedia.android.moodleonmobile.model.Thread;
import xyz.akedia.android.moodleonmobile.network.AddNewThread;

public class CourseDetailsActivity extends AppCompatActivity {

    public static String courseCode;
    FloatingActionButton fab;
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        courseCode = getIntent().getStringExtra("courseCode");
        toolbar.setTitle(courseCode);
        setSupportActionBar(toolbar);
        initUiElements();
    }
    private void initUiElements(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewThreadDialog();
            }
        });
        pager = (ViewPager) findViewById(R.id.pager);
        CourseDetailsAdapter pagerAdapter = new CourseDetailsAdapter(getSupportFragmentManager());
        String [] titles = {"Threads","Assignments","Grades"};
        pagerAdapter.setVals(titles, titles.length);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(titles.length - 1);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    fab.show();
                else
                    fab.hide();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_calendar) {
            showCalendar();
            return true;
        }
        if (id == R.id.action_info) {
            showInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showNewThreadDialog(){
        final Dialog dialog = new Dialog(CourseDetailsActivity.this,R.style.DialogSlideAnimBottom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_new_thread);
        dialog.findViewById(R.id.create_thread_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((TextView)dialog.findViewById(R.id.title)).getText().toString();
                String description = ((TextView)dialog.findViewById(R.id.description)).getText().toString();
                AddNewThread addNewThread = new AddNewThread(getNewThreadResponseHandler(dialog),title,description,courseCode);
                addNewThread.getAddNewThreadResponse();
            }
        });
        setDialogLayoutParams(dialog);
    }

    private void showCalendar(){
        Dialog dialog = new Dialog(CourseDetailsActivity.this,R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_event_viewer);
        ((TextView)dialog.findViewById(R.id.event_title)).setText(courseCode + " events");
        setDialogLayoutParams(dialog);
    }
    private void showInfo(){
        Dialog dialog = new Dialog(CourseDetailsActivity.this,R.style.DialogSlideAnimSmall);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_info);
        ((TextView)dialog.findViewById(R.id.course_code)).setText(courseCode);
        setDialogLayoutParams(dialog);
    }
    private void setDialogLayoutParams(Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private AddNewThread.AddNewThreadResponseHandler getNewThreadResponseHandler(final Dialog dialog){
        AddNewThread.AddNewThreadResponseHandler addNewThreadResponseHandler = new AddNewThread.AddNewThreadResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("addNewThread", response.toString());
                try{
                    boolean resp = response.getBoolean("success");
                    String msg = "Thread created successfully!";
                    if(!resp)
                        msg = "There was some error while creating the thread.Please try again!";
                    else {
                        dialog.dismiss();
                    }
                    Toast.makeText(CourseDetailsActivity.this,msg,Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Log.d("addNewThread","failure");
            }

            @Override
            public void onError(Exception e) {
                Log.d("addNewThread",e.toString());
                e.printStackTrace();
            }
        };
        return addNewThreadResponseHandler;
    }

}
