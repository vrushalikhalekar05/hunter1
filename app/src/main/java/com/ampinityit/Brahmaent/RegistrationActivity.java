package com.ampinityit.Brahmaent;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.telephony.TelephonyManager;

import static com.ampinityit.Brahmaent.LoginActivity.IME;
import static com.ampinityit.Brahmaent.LoginActivity.PREF_SKIP_LOGIN;

public class RegistrationActivity extends AppCompatActivity {

    Button btnRegister;
    ConnectionDetector connectionDetector;
    String jsonResponce, jsonResponce1, jsonResponce2, jsonResponce3, jsonResponce4;
    EditText txtFullName;
    EditText txtAddress;
    EditText txtEmail;
    EditText txtMobile;
    EditText txtPanCard;
    EditText txtAdharNo;
    String strtxtFullName, strtxtAddress, strtxtEmail, strtxtMobile, strtxtPanCard, strtxtAdharNo, strtxtIMEI;
    private String UserName1, Password1;
    SharedPreferencesConstants sharedPreferencesConstants;
    private String IMEI;
    private SharedPreferences mSharedPreferences;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private EditText editText;
    public static final String PREFERENCE= "preference";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registration);


        //trigger 'loadIMEI'
        loadIMEI();
        /** Fading Transition Effect */
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Called when the 'loadIMEI' function is triggered.
     */
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


    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(RegistrationActivity.this)
                    .setTitle("Permission Request")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(RegistrationActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })

                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                doPermissionGrantedStuffs();
            } else {

            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(RegistrationActivity.this)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
                .show();
    }


    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        String IMEINumber = tm.getDeviceId();


        // Now read the desired content to a textview.
        editText = (EditText) findViewById(R.id.txtIMEI);
        editText.setText(IMEINumber);











    Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectionDetector = new ConnectionDetector(getApplicationContext());
        txtFullName= (EditText) findViewById(R.id.txtFullName);
        txtAddress= (EditText) findViewById(R.id.txtAddress);
        txtEmail= (EditText) findViewById(R.id.txtEmail);
        txtMobile= (EditText) findViewById(R.id.txtMobile);
        txtPanCard= (EditText) findViewById(R.id.txtPanCard);
        txtAdharNo= (EditText) findViewById(R.id.txtAdharNo);
        btnRegister= (Button) findViewById(R.id.btnRegister);

      // txtIMEI = (EditText) findViewById(R.id.txtIMEI);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                validate();

            }
        });


    }
    public void validate() {


        strtxtFullName = txtFullName.getText().toString().trim();
        strtxtAddress=txtAddress.getText().toString().trim();
        strtxtEmail=txtEmail.getText().toString().trim();
        strtxtMobile=txtMobile.getText().toString().trim();
        strtxtPanCard=txtPanCard.getText().toString().trim();
        strtxtAdharNo=txtAdharNo.getText().toString().trim();
      //  strtxtIMEI=strtxtIMEI.toString().trim();
        strtxtIMEI=editText.getText().toString().trim();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext(), "please connect to internet", Toast.LENGTH_SHORT).show();
        } else if (strtxtFullName.length() == 0  ) {
            Toast.makeText(getApplicationContext(), "Please Enter valid FullName", Toast.LENGTH_SHORT).show();
        } else if (strtxtAddress.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please Enter valid Address", Toast.LENGTH_SHORT).show();
        }


     else if (strtxtEmail.length() == 0) {
        Toast.makeText(getApplicationContext(), "Please Enter valid Email", Toast.LENGTH_SHORT).show();
    }
        else if (strtxtMobile.length() == 0  ) {
            Toast.makeText(getApplicationContext(), "Please Enter valid Mobile No", Toast.LENGTH_SHORT).show();
        }  else if (strtxtPanCard.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please Enter valid PanCard", Toast.LENGTH_SHORT).show();
        }
      else if (strtxtAdharNo.length() == 0) {
        Toast.makeText(getApplicationContext(), "Please Enter valid Adhar No", Toast.LENGTH_SHORT).show();
    }
        else if (strtxtIMEI.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please Enter valid IMEI", Toast.LENGTH_SHORT).show();
        } else {
            new RegistrationActivity.RegistrationFormAsyncTask().execute();
        }
    }
    public class RegistrationFormAsyncTask extends AsyncTask<String, Integer, String>
    {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(RegistrationActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
            dialog.dismiss();
            System.out.println("result is"+Result);

            System.out.println("in postexecute registration");
            //SharedPreferencesConstants.getInstance(getApplicationContext()).erase();
            //   [{"UserName":"TW778994435","Password":587448.0,"FullName":"ACHUT ADE","MobileNo":9766327427.0}]
            if (SharedPreferencesConstants.getInstance(getApplicationContext()).registered()){
                Toast.makeText(getApplicationContext(),"User Already Registered",Toast.LENGTH_LONG).show();
                System.out.println("already registyered");
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                btnRegister.setVisibility(View.INVISIBLE);
            }
            else {
                SharedPreferencesConstants.getInstance(getApplicationContext()).userRegister(strtxtIMEI);
                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                System.out.println("Registered Successfully");
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);

            }
            /*Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            btnRegister.setVisibility(View.INVISIBLE);*/


        }

        @Override
        protected String doInBackground(String... args) {

            System.out.println("in registration background");
            String url =sharedPreferencesConstants.KEY_SP_BASE_URL+"Empregistration.asmx";
            String soapactionaddlob ="http://tempuri.org/Insert_EmpRegiList";
            String methodname="Insert_EmpRegiList";
            String namespacesaalob="http://tempuri.org/";
            try {


                SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);

                requestAddlob.addProperty("EmplyeeName", strtxtFullName);
                requestAddlob.addProperty("Address", strtxtAddress);
                requestAddlob.addProperty("MobileNo", strtxtMobile);
                requestAddlob.addProperty("EmailID", strtxtEmail);
                requestAddlob.addProperty("PanCard", strtxtPanCard);
                requestAddlob.addProperty("AdharNo", strtxtAdharNo);
                requestAddlob.addProperty("IMEI", strtxtIMEI);


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


            } catch (Exception e) {

                //   Log.e("IOLOG", e.getMessage());
                e.printStackTrace();
//                dialog.dismiss();
            }


            System.out.println("json responce in background"+jsonResponce);
            //System.out.println("before json responce registration");

            return jsonResponce;
        }



    }
}
