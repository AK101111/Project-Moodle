package xyz.akedia.android.moodleonmobile.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.Fragments.CourseAssignmentFragment;
import xyz.akedia.android.moodleonmobile.Fragments.CourseGradesFragment;
import xyz.akedia.android.moodleonmobile.Fragments.CourseThreadsFragment;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseDetailsAdapter extends FragmentPagerAdapter {
    String[] Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    public CourseDetailsAdapter(FragmentManager fm) {
        super(fm);

    }
    public void setVals(String[] mTitles, int mNumbOfTabsumb){
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    private List<String[]> getDummyCourseThreadList(){
        List<String[]> threadList = new ArrayList<>();
        for(int i = 0; i < 12 ; i++){
            String[] thread = {"Dummy thread title","Dummy thread content, dummy thread content, dummy thread content\nDummy thread content","14-02-2016"};
            threadList.add(thread);
        }
        return threadList;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                CourseThreadsFragment tabCourseThread = new CourseThreadsFragment();
                //put the courseThreadList here
                //tabCourseThread.setVals(getDummyCourseThreadList());
                fragment = tabCourseThread;
                break;
            case 1:
                fragment = new CourseAssignmentFragment();
                break;
            case 2:
                fragment = new CourseGradesFragment();
                break;
        }
        return fragment;
    }

    // This method returns the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method returns the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
