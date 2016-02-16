package xyz.akedia.android.moodleonmobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.Tabs.TabCourseList;
import xyz.akedia.android.moodleonmobile.Tabs.TabGrades;
import xyz.akedia.android.moodleonmobile.Tabs.TabNotifications;

/**
 * Created by ashish on 15/2/16.
 */
public class HomeScreenViewPagerAdapter extends FragmentPagerAdapter {
    String[] Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public HomeScreenViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    public void setVals(String[] mTitles, int mNumbOfTabsumb){
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    private List<String[]> getDummyCourseList(){
        List<String[]> courseList = new ArrayList<>();
        String[] codes = {"CSL100","COP290","TXL361","EEL324","TXL371","HUL362"};
        String[] names = {"Introduction to Computer Science"
                        ,"Design Practices"
                        ,"Evaluation of Textile Material"
                        ,"Some EE course"
                        ,"Some TX course"
                        ,"Organizational Behaviour"};
        for(int i = 0; i < codes.length ; i++){
            String[] data = {codes[i],names[i]};
            courseList.add(data);
        }
        return courseList;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                TabCourseList tabCourseList = new TabCourseList();
                //put a List<String[]> here
                // String[] contains course code,name
                tabCourseList.setVals(getDummyCourseList());
                fragment = tabCourseList;
                break;
            case 1:
                fragment = new TabNotifications();
                break;
            case 2:
                fragment = new TabGrades();
                break;
        }
        return fragment;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
