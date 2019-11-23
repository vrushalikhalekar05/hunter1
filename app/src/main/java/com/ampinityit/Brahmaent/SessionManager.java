package com.ampinityit.Brahmaent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.EditText;

public class SessionManager {
    // Users name (make variable public to access from outside)

    public static final String KEY_username= "username";
    public static final String KEY_password ="password";

    private static final String PREF_NAME = "Hunter";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Constructor

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     */
    public void createLoginSession(String username,String password ) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_username, username);
        editor.putString(KEY_password, password);

        editor.commit();
    }
    public void UpdatePass(String Pass) {
        editor.putString(KEY_password, Pass);
        editor.commit();
    }

    public boolean checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {

            return false;
        }
        return true;
    }
    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    private void startActivity(Intent startMain) {
        // TODO Auto-generated method stub
    }
    /**
     * Get stored session data
     */


    public String getKEY_username() {
        // user name
        String usetname = pref.getString(KEY_username, "");
        // String pass = pref.getString(KEY_PASSWORD, null);
        // return user
        return usetname;
    }

    public String getKEY_password() {
        // user name
        String password = pref.getString(KEY_password, "");
        // String pass = pref.getString(KEY_PASSWORD, null);
        // return user
        return password;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public void createLoginSession(EditText editText) {
    }
}
