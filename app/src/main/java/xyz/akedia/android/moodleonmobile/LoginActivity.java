package xyz.akedia.android.moodleonmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.utils.LoginHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();
    private static MoodleOnMobile appContext;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Android added it. Node idea why. //TODO figure why?
        appContext = (MoodleOnMobile) getApplicationContext();
    }

    private void onLoginSuccess(JSONObject userData) {
        try {
            Log.d(TAG,userData.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
        //TODO
    }

    private void onLoginFailure() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Login failed");
        //TODO
    }

    private void onLoginError(Exception exception) {
        Toast.makeText(this, "Error:"+exception, Toast.LENGTH_LONG).show();
        Log.e(TAG, "Error during login: " + exception);
        exception.printStackTrace();
        //TODO
    }


    public void doLogin(View view) {
        //TODO check if all data is filled and only then allow login
        LoginHelper.ResponseHandler responseHandler = new LoginHelper.ResponseHandler() {
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
        LoginHelper loginHelper = new LoginHelper(appContext.getMoodleUrl(),getFilledUsername(),getFilledPassword(),appContext.requestQueue,responseHandler);
        loginHelper.sendLoginRequest();
        //TODO cancel login requests on back button pressed.
    }

}
