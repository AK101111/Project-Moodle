package xyz.akedia.android.moodleonmobile;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * To display settings. Such as :
 *  - Moodle url
 *  - etc.
 * References to create settings:
 *  - http://developer.android.com/guide/topics/ui/settings.html#Activity
 *  - http://viralpatel.net/blogs/android-preferences-activity-example/
 * @author Abhishek Kedia
 * TODO Use Prefernce Fragement instead of PreferenceActivity as recommended in http://developer.android.com/guide/topics/ui/settings.html#Activity
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences); //deprecated because it is recommended to use fragment instead of activity
    }
}
