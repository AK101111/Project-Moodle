package xyz.akedia.android.moodleonmobile.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.akedia.android.moodleonmobile.R;

/**
 * Created by ashish on 21/2/16.
 */
public class CourseGradesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_grades_fragment,container,false);
        return v;
    }
}
