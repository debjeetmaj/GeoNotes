package maj.geon.geonotes;

/**
 * Created by amit on 13/04/16.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import maj.geon.geonotes.util.GNConstants;
import maj.geon.geonotes.util.GeoRegistrationIntentService;
import maj.geon.geonotes.util.SessionManager;


/**
 * Android login screen Activity
 */
public class FBLoginActivity extends Activity {

    private static final String DUMMY_CREDENTIALS = "user@test.com:hello";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String LOG = "HomeLoginActivity : ";
    String CUSTOM_PROVIDER_NAME = "Facebook";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        // facebook login init
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_login);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile =  Profile.getCurrentProfile();

        if(accessToken != null) {
            Log.d("Check Login Status", "Already logged in");
            try {
                session.setKeyFbid(accessToken.getUserId());
                if (profile != null) {
                    Log.i("sas", "user profile is " + profile.getName());
                }
                Log.i("sas", "user already logged in " + accessToken);

                switchActivity();

            } catch (Exception e) {

            }
        }

        if (checkPlayServices()) {
            Intent intent = new Intent(this, GeoRegistrationIntentService.class);
            startService(intent);
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                String textStr = "User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken();
                Log.i("LoginActivity", textStr);
                session.setKeyFbid(loginResult.getAccessToken().getUserId());
                session.setKeyName(Profile.getCurrentProfile().getName());
                switchActivity();
            }

            @Override
            public void onCancel() {
                Log.i("LoginActivity", "cancelled login");
            }

            @Override
            public void onError(FacebookException e) {
                Log.i("LoginActivity", "error on logging");
            }

        });

    }

    // facebook on login success
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("Check Play Services", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    // switch activity
    private void switchActivity() {
        try {
            finish();
            Intent intent = new Intent(getApplicationContext(), GNLoginActivity.class);
            startActivity(intent);
        } catch(Exception e) {
            Log.e("err switching activity ", e.toString());
        }
    }
}

