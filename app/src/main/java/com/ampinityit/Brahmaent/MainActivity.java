package com.ampinityit.Brahmaent;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import static com.ampinityit.Brahmaent.SharedPreferencesConstants.SHARED_PREF_NAME;
import static com.ampinityit.Brahmaent.SharedPreferencesConstants.UserName;
import static com.ampinityit.Brahmaent.StoreData.arrayLenght;
import java.util.ArrayList;
import static com.ampinityit.Brahmaent.ProfileActivity.user_profile_name;

import static com.ampinityit.Brahmaent.SharedPreferencesConstants.KEY_SP_USERNAME;
//import static com.ampinityit.Brahmaent.SharedPreferencesConstants.getInstance;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String i,i1;
    int i11,i12;

    private String jsonResponce;
    private EditText search, search1;
    //private TextView username;
    private SharedPreferencesConstants sharedPreferencesConstants;
    //private SessionManager sessionManager;
    private TextView idtextviewtotalincome,inDatabase;
   /* public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";*/
    public static String  Column1;
    private DBHelper mydb ;
   //public String str1;
   //public long offlineData;
   Button insert;
    //String offD;
    private TextView username,email;
    private Context mContext;
    int count;
    String count1;
    String EmplyeeName,EmailID;
    ArrayList<String> records;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        insert=(Button) findViewById(R.id.data);
        mydb = new DBHelper(this);
        records = new ArrayList<String>();
        records = mydb.getAllCotacts();
        count = records.size();
        count1 = Integer.toString(count);
        username=(TextView) findViewById(R.id.username);
        email=(TextView) findViewById(R.id.email);
        new ProfileFormAsyncTask().execute();
        //getOfflineDataCount();
        //System.out.println("in main activity"+str1);
        //inDatabase.setText("vrushali");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       // inDatabase.setText(str1);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*sessionManager = new SessionManager(MainActivity.this);
        username = navigationView.getHeaderView(0).findViewById(R.id.username);
        email = navigationView.getHeaderView(0).findViewById(R.id.email);
        username.setText(sessionManager.getKEY_username());*/

        search = findViewById(R.id.search);
        search1 = findViewById(R.id.search1);
       inDatabase=findViewById(R.id.InYard);


        idtextviewtotalincome = findViewById(R.id.idtextviewtotalincome);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VehicleInfoSearchDeatilsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VehicleInfoSearchDeatilsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*offlineData=mydb.getProfilesCount();
        offD=Long.toString(offlineData);*/
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            //inDatabase.setText(str1);
           idtextviewtotalincome.setText("");
            inDatabase.setText(count1);
            i=inDatabase.getText().toString();
            i11=Integer.parseInt(i);
            //System.out.println("in mainactivitly ...when offline");
            //inDatabase.setText(offD);
            //System.out.println("offline data set");
            Toast.makeText(getApplicationContext(), "please connect to internet", Toast.LENGTH_SHORT).show();
        }
        else {
            //inDatabase.setText(offD);
            new DashboardFormAsyncTask().execute();
        }

    }
    /*public void getOfflineDataCount() {
        ArrayList<String> records = new ArrayList<String>();
        records = mydb.getAllCotacts();
        int count=records.size();
        String count1=Integer.toString(count);
        System.out.println(records);
        inDatabase.setText(count1);
    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // Setting Alert Dialog Title
            alertDialogBuilder.setTitle("Confirm Exit..!!!");
            // Icon Of Alert Dialog
            alertDialogBuilder.setIcon(R.drawable.questionsmarks);
            // Setting Alert Dialog Message
            alertDialogBuilder.setMessage("Are you sure,You want to exit");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                    finishAffinity();

                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle("Alert....!");
            builder.setMessage("Confirm Logout")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    SharedPreferencesConstants.getInstance ( getApplicationContext() ).logout ();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);

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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Dashboard) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.Vehicles) {
            Intent intent = new Intent(getApplicationContext(), VehicleInfoSearchDeatilsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle("Alert....!");
            builder.setMessage("Confirm Logout")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    SharedPreferencesConstants.getInstance(getApplicationContext()).logout();
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

        } else if (id == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.ImportData) {
            Intent intent = new Intent(getApplicationContext(), StoreData.class);
            startActivity(intent);
            finish();
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        public class DashboardFormAsyncTask extends AsyncTask<String, Integer, String> {

            ProgressDialog dialog;



            @Override
            protected void onPreExecute() {
               // inDatabase.setText(offD);
                super.onPreExecute();
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Please Wait...");
                dialog.show();
                dialog.setCancelable(false);
                //inDatabase.setText(str1);

            }

            @Override
            protected void onPostExecute(String Result) {
                super.onPostExecute(Result);
                dialog.dismiss();
                System.out.println("in postexcute of mainactivity");
               // ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                /*if (!cd.isConnectingToInternet()) {
                    ArrayList<String> records = new ArrayList<String>();
                    records = mydb.getAllCotacts();
                    int count = records.size();
                    String count1 = Integer.toString(count);
                    System.out.println(records);
                    inDatabase.setText(count1);
                    inDatabase.setText(offD);
                }*/
                inDatabase.setText(count1);

                if (Result != null)
                    try {
                        JSONObject root = new JSONObject(Result);

                        JSONArray array = root.getJSONArray("Table");

                        JSONObject object = array.getJSONObject(0);
                        Column1 = object.getString("Column1");

                        idtextviewtotalincome.setText(Column1);
                        //
                        // inDatabase.setText(str1);
                       //i=inDatabase.getText().toString();
                        //i11=Integer.parseInt(i);
                        i1=idtextviewtotalincome.getText().toString();
                        i12=Integer.parseInt(i1);
                        if(i11!=i12){
                            //insert.setVisibility(View.GONE);
                            insert.setVisibility(View.VISIBLE);


                            Animation anim = new AlphaAnimation(0.0f, 1.0f);
                            anim.setDuration(5); //You can manage the blinking time with this parameter
                            anim.setStartOffset(700);
                            anim.setRepeatMode(Animation.REVERSE);
                            anim.setRepeatCount(Animation.INFINITE);
                            insert.startAnimation(anim);

                            insert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(MainActivity.this, "Import data", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(MainActivity.this,StoreData.class);
                                    startActivity(i);
                                    finish();


                                }
                            });

                        }
                        else{
                            //insert.setVisibility(View.GONE);
                            insert.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "No need to import data", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                else {
                    Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            protected String doInBackground(String... args) {
                /*ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                if (!cd.isConnectingToInternet()) {

                   //inDatabase.setText(offD);
                    Toast.makeText(getApplicationContext(),"you are offline",Toast.LENGTH_SHORT).show();
                }
                else {*/

                    String url = sharedPreferencesConstants.KEY_SP_BASE_URL + "dashboard.asmx";
                    String soapactionaddlob = "http://tempuri.org/Total_Count";
                    String methodname = "Total_Count";
                    String namespacesaalob = "http://tempuri.org/";
                    try {


                        SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);


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

               // }
                return jsonResponce;
            }


        }
    public class  ProfileFormAsyncTask extends AsyncTask<String, Integer, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
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
                               // Address= jsonObject1.getString("Address");
                                EmailID = jsonObject1.getString("EmailID");
                               // MobileNo= jsonObject1.getString("MobileNo");
                                //PanCard= jsonObject1.getString("PanCard");
                                //AdharNo= jsonObject1.getString("AdharNo");
                                //String IMEI= jsonObject1.getString("IMEI");


                               // username.setText("vrushali");
                                //user_profile_short_emailid.setText(EmailID);
                               // name.setText(EmplyeeName);
                               // addr.setText(Address);
                               // email.setText("vrushalikhalekar05@gmail.com");
                               // mob.setText(MobileNo);
                                //adhar.setText(AdharNo);
                                //pan.setText(PanCard);


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
                        Toast.makeText(MainActivity.this,"couldnt call to webservice",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(MainActivity.this,"Result is empty",Toast.LENGTH_SHORT).show();
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

