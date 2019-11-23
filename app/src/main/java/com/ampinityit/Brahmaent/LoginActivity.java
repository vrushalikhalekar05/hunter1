package com.ampinityit.Brahmaent;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class LoginActivity extends Activity {

    private TextView Loginbutton, Register;
    private ProgressDialog dialog;
    String jsonResponce;
    String FullName, MobileNo;
    String stridEditTextEmail, stridEditTextPassword;
    // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferencesConstants sharedPreferencesConstants;
    private SessionManager sessionManager;

    private SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String IME = "ime";
    public static final String PREF_SKIP_LOGIN = "skip_login";

    String IMEINumber;
    private String IMEI;
    private EditText editText;
    TelephonyManager tel;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_loginactivity);
        // UserName = findViewById(R.id.usrusr);
        // Password = findViewById(R.id.pswrdd);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Register = findViewById(R.id.Register);
        sessionManager = new SessionManager(LoginActivity.this);
        dialog = new ProgressDialog(LoginActivity.this);

        Loginbutton = findViewById(R.id.lin);
        editText = findViewById(R.id.edit);
        loadIMEI();

        /*if (SharedPreferencesConstants.getInstance(getApplicationContext()).registered()){
            Register.setVisibility(View.INVISIBLE);
        }*/
        /*if(SharedPreferencesConstants.getInstance ( this ).isLoggedIn ()){
            finish ();
            startActivity ( new Intent ( this,ProfileActivity.class ) );
            return;
        }*/
        //editText.setEnabled(false);
        /** Fading Transition Effect */
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        //if (loadIMEI(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

        //  return;
        //}
        tel = (TelephonyManager) getSystemService(SplashActivity.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        IMEI = tel.getDeviceId().toString();


        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (IMEI.length() > 0 ) {

                  //  validate();
                  // IMEI = editText.getText().toString().trim();

                   ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                   if (!cd.isConnectingToInternet()) {
                       Toast.makeText(getApplicationContext(), "please connect to internet", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Intent intent = new Intent(LoginActivity.this, VehicleInfoSearchDeatilsActivity.class);
                       startActivity(intent);
                       finish();
                       //Toast.makeText(getApplicationContext(),IMEI,Toast.LENGTH_LONG).show();
                       //new LoginAsyncTask().execute();
                   }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter UserName and Password...!!! ", Toast.LENGTH_LONG).show();
                }
               //Toast.makeText(getApplicationContext(),"pressed",Toast.LENGTH_LONG).show();
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                    startActivity(intent);
            }

        });

    }

    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }




    private void requestReadPhoneStatePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Permission Request")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })

                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                doPermissionGrantedStuffs();
            } else {

            }
        }

    }


    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         IMEINumber = tm.getDeviceId();

        editText = (EditText) findViewById(R.id.edit);
        editText.setText(IMEINumber);



    }




    public void validate() {


        IMEI = editText.getText().toString().trim();

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext(), "please connect to internet", Toast.LENGTH_SHORT).show();
        }
    }



    public void loginUser(String IMEI) {

        Log.e("On click on check", "On click on check");
        new LoginAsyncTask().execute();

    }
    public class LoginAsyncTask extends AsyncTask<String, Integer, String>
    {

        ProgressDialog dialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCancelable(false);


        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
            dialog.dismiss();


            //   [{"UserName":"TW778994435","Password":587448.0,"FullName":"ACHUT ADE","MobileNo":9766327427.0}]


            System.out.println(Result);
            if (Result != null) {
                try {

                    JSONArray jsonArray = new JSONArray(Result);
                    int arrayLenght = jsonArray.length();
                    if(arrayLenght>0) {
                        JSONObject jsonObject =jsonArray.getJSONObject(0);// new JSONObject(Result);
                        Log.e("jsonObject", "jsonObject: "+jsonObject);
                        IMEI =jsonObject.getString("IMEI");
                        editText.setText(IMEI);
                        SharedPreferencesConstants.getInstance(getApplicationContext()).userLogin(IMEI);

                        // Password1=jsonObject.getString("Password");
                        Toast.makeText(LoginActivity.this,"Login Succesfully",Toast.LENGTH_SHORT).show();

                        //sessionManager.createLoginSession(IMEI);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Enter Valid Register IMI",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(LoginActivity.this,"Enter Valid Register ImEI",Toast.LENGTH_SHORT).show();
            }






                        // Password1=jsonObject.getString("Password");
            //Toast.makeText(LoginActivity.this,"Login Succesfully",Toast.LENGTH_SHORT).show();

                        //sessionManager.createLoginSession(IMEI);

                        /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();*/
               /*     }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Enter Valid Register IMI",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(LoginActivity.this,"Enter Valid Register ImEI",Toast.LENGTH_SHORT).show();
            }
*/
        }

        @Override
        protected String doInBackground(String... args) {

            String url =sharedPreferencesConstants.KEY_SP_BASE_URL+"newlogin.asmx";
            String soapactionaddlob ="http://tempuri.org/getLogin";
            String methodname="getLogin";
            String namespacesaalob="http://tempuri.org/";
            try {


                SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);

                requestAddlob.addProperty("IMEI",IMEI);
                //requestAddlob.addProperty("UserName", stridEditTextEmail);
                //requestAddlob.addProperty("Password", stridEditTextPassword);
               //requestAddlob.addProperty("IMEI :",IMEI);


                //requestAddlob.addProperty("IMEI", "123456");

                SoapSerializationEnvelope envelopeAddlob = new SoapSerializationEnvelope(SoapEnvelope.VER11);


                envelopeAddlob.encodingStyle = "utf-8";
                envelopeAddlob.implicitTypes = true;
                envelopeAddlob.setOutputSoapObject(requestAddlob);
                envelopeAddlob.dotNet = true;
                SoapPrimitive result1=null;


                    HttpTransportSE httpTransportAddlob = new HttpTransportSE(url);
                    httpTransportAddlob.debug = true;
                    httpTransportAddlob.call(soapactionaddlob, envelopeAddlob);
                    result1 = (SoapPrimitive) envelopeAddlob.getResponse();
                    Log.i("RESPONSE after add", String.valueOf(result1));
                    jsonResponce= result1.toString();
                    Log.i("RESPONSE after add", jsonResponce);



            } catch (Exception e) {


                e.printStackTrace();

            }





            return jsonResponce;
        }



    }
}
