package xyz.akedia.android.moodleonmobile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.Adapters.HomeScreenViewPagerAdapter;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.uiElements.UserDetailsDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUiElements();
    }

    private void initUiElements(){
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        HomeScreenViewPagerAdapter pagerAdapter = new HomeScreenViewPagerAdapter(getSupportFragmentManager());
        String [] titles = {"Courses","Notifications","Grades"};
        pagerAdapter.setVals(titles, titles.length);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(titles.length - 1);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_profile) {
            showProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showCalendar(){
        Dialog dialog = new Dialog(MainActivity.this,R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_event_viewer);
        setDialogLayoutParams(dialog);
    }

    private void onUserLogout() {
        MoodleOnMobile.App.clearLoginCredentials();
        Intent i = new Intent(this,StartActivity.class);
        //TODO clear user object
        startActivity(i);
        MainActivity.this.finish();
    }

    private void showProfile(){
        Dialog dialog = new Dialog(MainActivity.this,R.style.DialogSlideAnimSmall);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_settings);
        setDialogLayoutParams(dialog);
        new UserDetailsDialog(dialog, new UserDetailsDialog.LogoutHandler() {
            @Override
            public void onLogout(JSONObject logoutResponse) {
                onUserLogout();
            }
            @Override
            public void onError(Exception e) {
                //TODO //case when logout unsucessfull
                e.printStackTrace();
                onUserLogout();
            }
        }).setUpDialog();
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
