package xyz.akedia.android.moodleonmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

public class CourseInfoActivity extends AppCompatActivity {

    String courseCode;
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
        pager.setOffscreenPageLimit(titles.length-1);

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

}
