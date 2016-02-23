package xyz.akedia.android.moodleonmobile.uiElements;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Calendar;

import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.network.LogoutHelper;

/**
 * Created by akedia on 23/02/16.
 */
public class UserDetailsDialog {
    private final Dialog dialog;
    private final TextView mUserName;
    private final TextView mEntryNumber;
    private final TextView mEmail;
    private final TextView mCurrentSemester;
    private final Button mLogoutButton;
    private final LogoutHandler logoutHandler;

    public interface LogoutHandler {
        void onLogout(JSONObject logoutResponse);
        void onError(Exception exception);
    }

    //TODO handle the case when either field is empty

    private void setUserName() {
        String firstName = MoodleOnMobile.getUser().getFirstName();
        String lastName = MoodleOnMobile.getUser().getLastName();
        if(firstName != null && lastName != null) {
            String name = firstName + " "  +lastName;
            mUserName.setText(name);
        }
    }
    private void setEntryNumber() {
        String entryNumberString = MoodleOnMobile.getUser().getEntryNumber();
        if(entryNumberString != null) {
            mEntryNumber.setText(entryNumberString);
        }
    }
    private void setEmail() {
        String email = MoodleOnMobile.getUser().getEmail();
        if(email != null) {
            mEmail.setText(email);
        }
    }
    private void setCurrentSemester() {
        int currentSemester = MoodleOnMobile.getUser().getCurrentSem();
        if(currentSemester != 0) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            String currentSemesterDisplay = String.format("Current Semester : %d%s (%d)",currentSemester,
                    (currentSemester==1)?"st":"nd",currentYear);
            mCurrentSemester.setText(currentSemesterDisplay);
        } else {
            //TODO case when current sem is still not known.
        }
    }
    private void setupLogoutButton() {
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutHelper(new LogoutHelper.LogoutResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject logoutResponse) {
                        logoutHandler.onLogout(logoutResponse);
                    }

                    @Override
                    public void onFailure() {
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                        logoutHandler.onError(exception);
                    }
                }).sendLogoutRequest();
            }
        });
    }


    public UserDetailsDialog(Dialog dialog, LogoutHandler logoutHandler) {
        this.dialog = dialog;
        this.logoutHandler = logoutHandler;
        mUserName = (TextView) dialog.findViewById(R.id.user_name);
        mEntryNumber = (TextView) dialog.findViewById(R.id.user_entry);
        mEmail = (TextView) dialog.findViewById(R.id.user_email);
        mCurrentSemester = (TextView) dialog.findViewById(R.id.current_semester);
        mLogoutButton = (Button) dialog.findViewById(R.id.logout_button);
    }

    public Dialog setUpDialog() {
        setUserName();
        setEntryNumber();
        setEmail();
        setCurrentSemester();
        setupLogoutButton();
        return dialog;
    }
}
