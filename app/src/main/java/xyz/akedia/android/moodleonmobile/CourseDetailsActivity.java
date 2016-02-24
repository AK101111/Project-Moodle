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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.w3c.dom.Text;

import xyz.akedia.android.moodleonmobile.Adapters.CourseDetailsAdapter;

public class CourseDetailsActivity extends AppCompatActivity {

    public static String courseCode;
    FloatingActionButton fab;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
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

}
