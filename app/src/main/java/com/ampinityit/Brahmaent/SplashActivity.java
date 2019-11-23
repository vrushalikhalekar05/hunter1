package com.ampinityit.Brahmaent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import android.telephony.TelephonyManager;

public class SplashActivity extends Activity {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private SessionManager sessionManager;


    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkAndRequestPermissions() {
        Context context;
        context = getApplicationContext();
        int locationpermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int readphonestatepermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        int permissionSendMessage = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
        int writepermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readexternalstoragepermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int coarsLocpermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (readphonestatepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readexternalstoragepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (coarsLocpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }



        return true;
    }

    @Override
    protected void onCreate(Bundle v) {
        // TODO Auto-generated method stub
        super.onCreate(v);
        setContentView(R.layout.activity_splash_screen);
        //sessionManager=new SessionManager(SplashActivity.this);
        /*if(SharedPreferencesConstants.getInstance ( this ).isLoggedIn ()){
            finish ();
            startActivity ( new Intent ( this,ProfileActivity.class ) );
            return;
        }*/
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (SharedPreferencesConstants.getInstance ( getApplicationContext() ).isLoggedIn ()) {
                        Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }else {
                        Intent intent2 = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        finish();

                    }
                }
            }
        };
        timer.start();
//		}
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "location granted");
                        }
                    } else if (permissions[i].equals(Manifest.permission.READ_PHONE_STATE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "read phone state granted");
                        }
                    } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "write external granted");
                        }
                    } else if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "read external storage granted");
                            //Toast.makeText(SplashActivity.this, "Please Restart the Application....... ", Toast.LENGTH_LONG).show();
                        }
                    } else if (permissions[i].equals(Manifest.permission.READ_LOGS)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "read logs granted");
                        }
                    } else if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "Coarse location granted ");
                            Intent im = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            im.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(im);
                            finish();
                        }
                    }
                }
            }
        }
    }
}
