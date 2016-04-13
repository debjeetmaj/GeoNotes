package maj.geon.geonotes.util;

/**
 * Created by amit on 13/04/16.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import maj.geon.geonotes.FBLoginActivity;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "GeoNotesPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_FBID = "fbID";
    public static final String KEY_GCMID = "gcmID";
    public static final String KEY_USER_TYPE = "userType";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */

    public void createLoginSession(String name, String fbID, String gcmID, String userType){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_GCMID, gcmID);
        editor.putString(KEY_FBID, fbID);
        editor.putString(KEY_USER_TYPE, userType);
        editor.commit();
    }

    // setters
    public void setKeyName (String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public void setKeyFbid(String fbID) {
        editor.putString(KEY_FBID, fbID);
        editor.commit();
    }

    public void setKeyGcmid(String gcmID) {
        editor.putString(KEY_GCMID, gcmID);
        editor.commit();
    }

    public void setKeyUserType(String userType) {
        editor.putString(KEY_USER_TYPE, userType);
        editor.commit();
    }

    // getters
    public String getKeyName() {
        return pref.getString(KEY_NAME, null);
    }

    public String getKeyFbid() {
        return pref.getString(KEY_FBID, null);
    }

    public String getKeyGcmid() {
        return pref.getString(KEY_GCMID, null);
    }

    public String getKeyUserType() {
        return pref.getString(KEY_USER_TYPE, null);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, FBLoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_FBID, pref.getString(KEY_FBID, null));
        user.put(KEY_GCMID, pref.getString(KEY_GCMID, null));
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, FBLoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
