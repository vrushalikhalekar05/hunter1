package com.ampinityit.Brahmaent;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 14-04-17.
 */

public class SharedPreferencesConstants {

    public static String KEY_SP_IMEI ;
    public static String mobilenum ;
    public static String UserName ;
    public static String Password ;
    public static final String KEY_SP_ID = "ID";
    public static final String KEY_SP_TOKEN="TOKEN";

    public static final String KEY_SP_BASE_URL ="http://brahmaweb.ampinityit.com/";

    public static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String SHARED_PREF_REGISTER = "register";
    public static final String KEY_SP_USERNAME = "USERNAME";
    //for registration
    public static final String KEY_SP_FULLNAME = "FULLNAME";
    public static final String KEY_SP_ADDRESS = "ADDRESS";
    public static final String KEY_SP_EMAIL = "EMAIL";
    public static final String KEY_SP_MOBILE = "MOBILE";
    public static final String KEY_SP_PANNO  = "PANNO";
    public static final String KEY_SP_ADHARNO = "ADHARNO";
    public static final String KEY_SP_IMEINO = "IMEINO";


    private static SharedPreferencesConstants mInstance;
    private static Context mCtx;
    private SharedPreferencesConstants(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPreferencesConstants getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesConstants (context);
        }
        return mInstance;
    }


    public boolean registered(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences ( SHARED_PREF_REGISTER,Context.MODE_PRIVATE );
        if(sharedPreferences.getString ( KEY_SP_IMEINO,null )!=null){
            return true;
        }
        return false;
    }
    public boolean userRegister(String ime){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences ( SHARED_PREF_REGISTER,Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString ( KEY_SP_IMEINO,ime );

        editor.apply ();
        return true;
    }
    public boolean erase(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences ( SHARED_PREF_REGISTER,Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.clear ();
        editor.apply ();
        return true;

    }
    //for login
    public boolean userLogin(String ime){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences ( SHARED_PREF_NAME,Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString ( KEY_SP_USERNAME,ime );

        editor.apply ();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences ( SHARED_PREF_NAME,Context.MODE_PRIVATE );
        if(sharedPreferences.getString ( KEY_SP_USERNAME,null )!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences ( SHARED_PREF_NAME,Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.clear ();
        editor.apply ();
        return true;

    }


}
