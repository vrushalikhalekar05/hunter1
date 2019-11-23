package com.ampinityit.Brahmaent;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class StoreData extends AppCompatActivity {
  //  String parsingdata="{\"Employee\":[{\"ID\",\"AccountNo\",\"salary\"}]}";
  String jsonResponce;
    TextView textView;
    ArrayList arrayList;
    ListView listView;

    long offlineData;
    String offData;
    private TextView txtView;
    private DBHelper mydb ;
    String str="";
    public static int arrayLenght;
    Button importData,showData;
    SharedPreferencesConstants sharedPreferencesConstants;
    //DottedProgressBar progressBar;
   /* private ProgressBar progressBar;
    TextView txt;
    Integer count =1;*/
   public static long count;
   Context c;
    TextView tvStatus;
    ArrayList regNo;
    List<String> l,l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        CoordinatorLayout  coordinatorLayout=(CoordinatorLayout) findViewById(R.id.store_layout);
        l = new LinkedList<>();
        l1 = new LinkedList<>();


        setTitle("Hunters");

        tvStatus = (TextView) findViewById(R.id.textViewStatus);
        //progressBar = (DottedProgressBar) findViewById(R.id.progress);
        importData=(Button) findViewById(R.id.btnStore) ;
        regNo = new ArrayList();
        //showData=(Button) findViewById(R.id.btnShow) ;
        //Database db=new Database(this);
        mydb = new DBHelper(this);
        importData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                offlineData=mydb.getProfilesCount();
                offData=Long.toString(offlineData);
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                if (!cd.isConnectingToInternet()) {

                    System.out.println(offData);
                    Toast.makeText(getApplicationContext(), "please connect to internet", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    System.out.println("in else of store data");
                    System.out.println(offData);
                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    int memoryClass = am.getMemoryClass();
                    Log.v("onCreate", "memoryClass:" + Integer.toString(memoryClass));
                    //new AsyncInsertData("fast").execute();
                    //Toast.makeText(getApplicationContext(),IMEI,Toast.LENGTH_LONG).show();
                   new StoreData.StoreAsyncTask().execute();
                }

                //Toast.makeText(getApplicationContext(),"success1",Toast.LENGTH_SHORT).show();




            }
        });



        /*showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ListActivity.class);
                startActivity(i);
                // Show all data


            }
        });*/
       // arrayList=database.fetchData();

        //ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.activity_list_item,android.R.id.text1,arrayList);

        //listView.setAdapter(adapter);
    }//end of oncreate
    /*public boolean onBackPressed() {
        CoordinatorLayout  coordinatorLayout=(CoordinatorLayout) findViewById(R.id.store_layout);
        if(coordinatorLayout.)){
            Intent

        }
        *//*if (coordinatorLayout.isDrawerOpen(GravityCompat.START)) {
            coordinatorLayout.closeDrawer(GravityCompat.START);
        }*//*
    }*/
    public void onBackPressed() {
        /*Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);*/
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public class StoreAsyncTask extends AsyncTask<String, String, String>
    {

        //ProgressDialog dialog;
        ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //tvStatus.setText("Inserting  records...");
           // progressBar.startProgress();
           // progressBar.isShown();
            dialog = new ProgressDialog(StoreData.this);
            dialog.setMessage("Please Wait...");
            System.out.println("in preexcute of store data");
            dialog.show();
            dialog.setCancelable(false);


        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
            System.out.println("in postexcute of store data");
            if (Result != null) {
               //mydb.deleteContact();

               //System.out.println("table deleted");
                //mydb.onUpgrade();
                try {
                    JSONObject root = new JSONObject(Result);

                    JSONArray jsonArray= root.getJSONArray("Table");
                    arrayLenght = jsonArray.length();
                    //System.out.println("a");
                    if(arrayLenght>0) {
                        try {
                          //  Toast.makeText(getApplicationContext(),"success2",Toast.LENGTH_SHORT).show();
                            for(int i=0;i<jsonArray.length();i++) {

                                System.out.println("count of for loop"+i);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                System.out.println(jsonObject1);
                               //String ID1 = jsonObject1.getString("ID");
                                String AccountNo1 = jsonObject1.getString("AccountNo");

                                String BRANCHNAME1 = jsonObject1.getString("BRANCHNAME");

                                String BUSREGION1 = jsonObject1.getString("BUSREGION");

                                String Allocation_MAR_APR191 = jsonObject1.getString("Allocation_MAR_APR19");

                                String TEAMLEADERNAME1 = jsonObject1.getString("TEAMLEADERNAME");

                                String TLMOBNO1 = jsonObject1.getString("TLMOBNO");

                                String RepoList1 = jsonObject1.getString("RepoList");

                                String CustomerFullName1 = jsonObject1.getString("CustomerFullName");

                                String VehicleMake1 = jsonObject1.getString("VehicleMake");

                                String VehicleModel1 = jsonObject1.getString("VehicleModel");

                                String RegistrationNo1 = jsonObject1.getString("RegistrationNo");

                                String ManufacturingYear1 = jsonObject1.getString("ManufacturingYear");

                                String ChassisNo1 = jsonObject1.getString("ChassisNo");

                                String EngineNo1 = jsonObject1.getString("EngineNo");

                                String POSINCR1 = jsonObject1.getString("POSINCR");

                                String CollectionDPD1 = jsonObject1.getString("CollectionDPD");

                                String MobileNo1 = jsonObject1.getString("MobileNo");

                                String PhoneNo1 = jsonObject1.getString("PhoneNo");

                                String CustomerAddress1 = jsonObject1.getString("CustomerAddress");

                                String City1 = jsonObject1.getString("City");

                                String PinCode1 = jsonObject1.getString("PinCode");

                                String ComapnyName1 = jsonObject1.getString("ComapnyName");


                               //int cnt=0;

                                /*if(l.size()==0){
                                    if(l.contains(RegistrationNo1)){
                                        System.out.println("data already present");
                                    }
                                    else {
                                        l.add(RegistrationNo1);
                                        mydb.insertContact(AccountNo1, BRANCHNAME1, BUSREGION1, Allocation_MAR_APR191, TEAMLEADERNAME1, TLMOBNO1, RepoList1, CustomerFullName1, VehicleMake1, VehicleModel1, RegistrationNo1, ManufacturingYear1, ChassisNo1, EngineNo1, POSINCR1, CollectionDPD1, MobileNo1, PhoneNo1, CustomerAddress1, City1, PinCode1, ComapnyName1);
                                    }
                                }
                                else{
                                    if(l.contains(RegistrationNo1)){
                                        System.out.println("data already present");
                                    }
                                    else{
                                        l.add(RegistrationNo1);
                                        mydb.insertContact(AccountNo1, BRANCHNAME1, BUSREGION1, Allocation_MAR_APR191, TEAMLEADERNAME1, TLMOBNO1, RepoList1, CustomerFullName1, VehicleMake1, VehicleModel1, RegistrationNo1, ManufacturingYear1, ChassisNo1, EngineNo1, POSINCR1, CollectionDPD1, MobileNo1, PhoneNo1, CustomerAddress1, City1, PinCode1, ComapnyName1);

                                    }
                                }*/
                                /*l.add(RegistrationNo1);
                                    if(l.contains(RegistrationNo1))
                                    {
                                        System.out.println("data already present");
                                        Toast.makeText(StoreData.this,"Data already present",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        //for(int k=0;k<5;k++) {
                                        System.out.println("data is inserting");
                                            l.add(RegistrationNo1);
                                            mydb.insertContact(AccountNo1, BRANCHNAME1, BUSREGION1, Allocation_MAR_APR191, TEAMLEADERNAME1, TLMOBNO1, RepoList1, CustomerFullName1, VehicleMake1, VehicleModel1, RegistrationNo1, ManufacturingYear1, ChassisNo1, EngineNo1, POSINCR1, CollectionDPD1, MobileNo1, PhoneNo1, CustomerAddress1, City1, PinCode1, ComapnyName1);
                                            count = mydb.getProfilesCount();
                                            System.out.println("total count" + count);
                                        //}
                                    }*/
                              boolean resultv;
                                //int hashOfL=l.hashCode();
                           /*int hashcode1= mydb.search(l);
                            if(hashcode1==l.hashCode()){*/
                                //System.out.println("data already present");
                                /*count= mydb.getProfilesCount();

                                System.out.println("total count"+count);*/
                            //}
                            /*else{
                                cnt++;*/
                               /* System.out.println("function called"+cnt);
                                l.add(RegistrationNo1);*/
                                /*mydb.insertContact(AccountNo1, BRANCHNAME1, BUSREGION1, Allocation_MAR_APR191, TEAMLEADERNAME1, TLMOBNO1, RepoList1, CustomerFullName1, VehicleMake1, VehicleModel1, RegistrationNo1, ManufacturingYear1, ChassisNo1, EngineNo1, POSINCR1, CollectionDPD1, MobileNo1, PhoneNo1, CustomerAddress1, City1, PinCode1, ComapnyName1);
                                System.out.println("new data inserting");*/
                           // }
                                resultv=mydb.hasObject(RegistrationNo1);
                                System.out.println(resultv);

                                if(!resultv) {
                                   mydb.insertContact(AccountNo1, BRANCHNAME1, BUSREGION1, Allocation_MAR_APR191, TEAMLEADERNAME1, TLMOBNO1, RepoList1, CustomerFullName1, VehicleMake1, VehicleModel1, RegistrationNo1, ManufacturingYear1, ChassisNo1, EngineNo1, POSINCR1, CollectionDPD1, MobileNo1, PhoneNo1, CustomerAddress1, City1, PinCode1, ComapnyName1);
                                    //new BigProcess(StoreData.this, ProgressDialog.STYLE_HORIZONTAL).execute();
                                    Toast.makeText(StoreData.this,"Data Inserted",Toast.LENGTH_LONG).show();
                                    Log.d("MyApp","I am here..data inserting");
                                }
                                else{
                                    //Toast.makeText(StoreData.this,"Data already exists",Toast.LENGTH_LONG).show();
                                    Log.d("MyApp","data already inserted");
                                }
                                //System.out.println("data inserting "+AccountNo1);

                                //if(isInserted == true)


                               // else
                                    //Toast.makeText(StoreData.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                               // database.insertData(ID,AccountNo,BRANCHNAME/*,Allocation_MAR_APR19,TEAMLEADERNAME,TLMOBNO,RepoList,CustomerFullName,VehicleMake,VehicleModel,RegistrationNo, ManufacturingYear,ChassisNo,EngineNo,POSINCR,CollectionDPD,MobileNo,PhoneNo,CustomerAddress,City,PinCode*/);
                                //Toast.makeText(getApplicationContext(),"stored data successfully",Toast.LENGTH_SHORT).show();
                                // str = "\n Employee" + ID+ "\n ID:" + AccountNo + "\n AccountNo:" + BRANCHNAME + "\n BRANCHNAME:" +  BUSREGION + "\n BUSREGION:" +Allocation_MAR_APR19 + "\n Allocation_MAR_APR19:"+TEAMLEADERNAME+"\nTEAMLEADERNAME:"+TLMOBNO+"\nTLMOBNO:"+RepoList+"\n:CustomerFullName"+VehicleMake+"\nVehicleMake:"+VehicleModel+"\nVehicleModel:"+RegistrationNo+"\nRegistrationNo:"+ManufacturingYear+"\nManufacturingYear:"+ChassisNo+"\nChassisNo:"+EngineNo+"\nEngineNo:"+POSINCR+"\nPOSINCR:"+CollectionDPD+"\nCollectionDPD:"+MobileNo+"\nMobileNo:"+PhoneNo+"\nPhoneNo:"+CustomerAddress+"\nCustomerAddress:"+City+"\nCity:"+PinCode+"\nPinCode:";
                                //Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                            }//eof for
                            dialog.dismiss();
                            //progressBar.stopProgress();
                           // tvStatus.setText("Done inserting  ms.");
                            Toast.makeText(StoreData.this,"Data Inserted",Toast.LENGTH_LONG).show();


                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }//eof try
                        catch (JSONException e)


                        {
                            e.printStackTrace();
                        }

                        /*IMEI =jsonObject.getString("IMEI");
                        editText.setText(IMEI);*/

                        // Password1=jsonObject.getString("Password");
                        //Toast.makeText(StoreData.this,"Login Succesfully",Toast.LENGTH_SHORT).show();

                        //sessionManager.createLoginSession(IMEI);

                       /* Intent intent = new Intent(StoreData.this, MainActivity.class);
                        startActivity(intent);
                        finish();*/
                    }
                    else
                    {
                        Toast.makeText(StoreData.this,"couldnt call to webservice",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(StoreData.this,"Enter Valid Register ImEI",Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected String doInBackground(String... args) {

            System.out.println("in background of store data");
            String url =sharedPreferencesConstants.KEY_SP_BASE_URL+"Importsdata.asmx";
            String soapactionaddlob ="http://tempuri.org/Importdata";
            String methodname="Importdata";
            String namespacesaalob="http://tempuri.org/";
            try {


                SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);


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
