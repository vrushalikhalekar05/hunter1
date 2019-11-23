package com.ampinityit.Brahmaent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import static com.ampinityit.Brahmaent.SharedPreferencesConstants.KEY_SP_USERNAME;
import static com.ampinityit.Brahmaent.SharedPreferencesConstants.SHARED_PREF_NAME;
import static com.ampinityit.Brahmaent.StoreData.arrayLenght;

public class ProfileActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private String jsonResponce;
    private SharedPreferencesConstants sharedPreferencesConstants;
    String fullName,Adhar,Email,Addr,Mob,Pan;
   TextView name,adhar,email,addr,mob,pan;
   static TextView user_profile_name,user_profile_short_emailid;
    String EmplyeeName,Address,EmailID,MobileNo,PanCard,AdharNo;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_profile_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Profile");
        sessionManager=new SessionManager(ProfileActivity.this);
        name=(TextView) findViewById(R.id.name);
        adhar=(TextView) findViewById(R.id.Usename);
        email=(TextView) findViewById(R.id.Emailid);
        addr=(TextView) findViewById(R.id.Address);
        mob=(TextView) findViewById(R.id.Phone);
        pan=(TextView) findViewById(R.id.PanCard);
        user_profile_name=(TextView) findViewById(R.id.user_profile_name);
        user_profile_short_emailid=(TextView) findViewById(R.id.user_profile_short_emailid);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        new ProfileFormAsyncTask().execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case  R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Alert....!");
                builder.setMessage("Confirm Logout")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        sessionManager.logoutUser();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public class ProfileFormAsyncTask extends AsyncTask<String, Integer, String>
    {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
            dialog.dismiss();


            if (Result != null)
                try {
                    JSONObject root = new JSONObject(Result);

                    JSONArray array= root.getJSONArray("Table");

                    JSONObject object= array.getJSONObject(0);
                    String MobileNo=object.getString("MobileNo");
                    String EmplyeeName=object.getString("EmplyeeName");
                    String Address=object.getString("Address");
                    String EmailID=object.getString("EmailID");
                    String PanCardst=object.getString("PanCard");
                    String AdharNost=object.getString("AdharNo");
                    String UserName=object.getString("UserName");
                    String Password=object.getString("Password");
                    String IMEI=object.getString("IMEI");

                    user_profile_name.setText(EmplyeeName);
                    user_profile_short_emailid.setText(EmailID);
                    name.setText(EmplyeeName);
                    Usename.setText(UserName);
                    Emailid.setText(EmailID);
                    Addresstx.setText(Address);
                    Phone.setText(MobileNo);
                    PanCard.setText(PanCardst);
                    AdharNo.setText(AdharNost);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            else {
                Toast.makeText(ProfileActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... args) {

            String url =sharedPreferencesConstants.KEY_SP_BASE_URL+"dashboard.asmx";
            String soapactionaddlob ="http://tempuri.org/User_Details";
            String methodname="User_Details";
            String namespacesaalob="http://tempuri.org/";
            try {


                SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);

                requestAddlob.addProperty("UserName",sessionManager.getKEY_username());


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




            return jsonResponce;
        }



    }*/
    public class  ProfileFormAsyncTask extends AsyncTask<String, Integer, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String profile) {
            super.onPostExecute(profile);
            dialog.dismiss();
                /*if (Result != null)
                    try {
                        //inDatabase.setText(str1);
                        JSONObject root = new JSONObject(Result);
                        System.out.println("root value"+root);
                        JSONArray array = root.getJSONArray("Table");
                        JSONObject object = array.getJSONObject(0);
                        System.out.println("object value"+object);
                        String EmplyeeName= object.getString("EmplyeeName");
                        String Address= object.getString("Address");
                        String EmailID = object.getString("EmailID");
                        String MobileNo= object.getString("MobileNo");
                        String PanCard= object.getString("PanCard");
                        String AdharNo= object.getString("AdharNo");
                        String IMEI= object.getString("IMEI");
                        email.setText(EmailID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                else {
                    Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }*/

            if (profile != null) {
                System.out.println("profile value"+profile);
                try {
                    JSONObject profileRoot = new JSONObject(profile);
                    System.out.println("root value"+profileRoot);

                    JSONArray jsonArray= profileRoot.getJSONArray("Table");
                    arrayLenght = jsonArray.length();
                        /*SharedPreferences shared = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                        String channel = (shared.getString(KEY_SP_USERNAME, ""));*/
                    //System.out.println("imei value"+channel);
                    //System.out.println(KEY_SP_USERNAME);
                    //System.out.println("a");
                    if(arrayLenght>0) {
                        try {
                            //  Toast.makeText(getApplicationContext(),"success2",Toast.LENGTH_SHORT).show();
                            for(int i=0;i<jsonArray.length();i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                System.out.println("object value"+jsonObject1);
                                //System.out.println(jsonObject1);
                                //String ID1 = jsonObject1.getString("ID");
                                EmplyeeName= jsonObject1.getString("EmplyeeName");
                                Address= jsonObject1.getString("Address");
                                EmailID = jsonObject1.getString("EmailID");
                                MobileNo= jsonObject1.getString("MobileNo");
                                PanCard= jsonObject1.getString("PanCard");
                                AdharNo= jsonObject1.getString("AdharNo");
                                //String IMEI= jsonObject1.getString("IMEI");


                                user_profile_name.setText(EmplyeeName);
                                user_profile_short_emailid.setText(EmailID);
                                name.setText(EmplyeeName);
                                addr.setText(Address);
                                email.setText(EmailID);
                                mob.setText(MobileNo);
                                adhar.setText(AdharNo);
                                pan.setText(PanCard);


                            }
                            dialog.dismiss();

                        }//eof try
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(ProfileActivity.this,"couldnt call to webservice",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(ProfileActivity.this,"Result is empty",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... args) {

            String url = sharedPreferencesConstants.KEY_SP_BASE_URL + "dashboard.asmx";
            String soapactionaddlob = "http://tempuri.org/User_Details";
            String methodname = "User_Details";
            String namespacesaalob = "http://tempuri.org/";
            try {


                SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);

                //requestAddlob.addProperty("UserName", sessionManager.getKEY_username());
                // SharedPreferencesConstants shared = getSharedPreferencesConstants(PREF_NAME, MODE_PRIVATE);

                //  requestAddlob.addProperty("IMEI",SharedPreferencesConstants);
                SharedPreferences shared = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String channel = (shared.getString(KEY_SP_USERNAME, ""));
                System.out.println("in background"+channel);
                requestAddlob.addProperty("UserName",shared.getString(KEY_SP_USERNAME, ""));
                SoapSerializationEnvelope envelopeAddlob = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelopeAddlob.encodingStyle = "utf-8";
                envelopeAddlob.implicitTypes = true;
                envelopeAddlob.setOutputSoapObject(requestAddlob);
                envelopeAddlob.dotNet = true;
                SoapPrimitive result1 = null;

                HttpTransportSE httpTransportAddlob = new HttpTransportSE(url);
                httpTransportAddlob.debug = true;
                httpTransportAddlob.call(soapactionaddlob, envelopeAddlob);
                result1 = (SoapPrimitive) envelopeAddlob.getResponse();
                Log.i("RESPONSE after add", String.valueOf(result1));
                jsonResponce = result1.toString();

            } catch (Exception e) {
                //   Log.e("IOLOG", e.getMessage());
                e.printStackTrace();
//                dialog.dismiss();
            }
            return jsonResponce;
        }
    }
}
