package xyz.akedia.android.moodleonmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.network.LoginHelper;
import xyz.akedia.android.moodleonmobile.utils.ParseResponse;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();

    public String getFilledUsername() {
        return ((EditText) findViewById(R.id.username_entry)).getText().toString();
    }
    public String getFilledPassword() {
        return ((EditText) findViewById(R.id.password_entry)).getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Android added it. Node idea why. //TODO figure why?
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(checkSavedCredentials()) {
            performAutoLogin();
        }
    }

    private boolean checkSavedCredentials() {
        return MoodleOnMobile.App.isLoginCredentialsSaved();
    }
    private void performAutoLogin() {
        String userName = MoodleOnMobile.App.getLoginUsername();
        String password = MoodleOnMobile.App.gettLoginPassword();
        if(!userName.isEmpty() && !password.isEmpty()) {
            performLogin(userName,password);
        }
    }

    private void saveUserData(JSONObject userData) {
        try {
            MoodleOnMobile.setUser(ParseResponse.parseUserData(userData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onLoginSuccess(JSONObject userData) {
        try {
            Log.d(TAG, userData.toString(4));
            saveUserData(userData);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
        //TODO
    }

    private void onLoginFailure() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Login failed");
        //TODO
    }

    private void onLoginError(Exception exception) {
        Toast.makeText(this, "Error:"+exception, Toast.LENGTH_LONG).show();
        Log.e(TAG, "Error during login: " + exception);
        exception.printStackTrace();
        //TODO
    }

    private void performLogin(String userName, String password) {
        //TODO check if all data is filled and only then allow login
        LoginHelper.LoginResponseHandler loginResponseHandler = new LoginHelper.LoginResponseHandler() {
            @Override
            public void onSuccess(JSONObject userData) {
                LoginActivity.this.onLoginSuccess(userData);
            }

            @Override
            public void onFailure() {
                LoginActivity.this.onLoginFailure();
            }

            @Override
            public void onError(Exception exception) {
                LoginActivity.this.onLoginError(exception);
            }
        };
        LoginHelper loginHelper = new LoginHelper(userName, password, loginResponseHandler);
        loginHelper.sendLoginRequest();
        //TODO cancel login requests on back button pressed.
    }

    public void doLogin(View view) {
        MoodleOnMobile.App.saveLoginCredentials(getFilledUsername(),getFilledPassword());
        performLogin(getFilledUsername(),getFilledPassword());
    }

}
