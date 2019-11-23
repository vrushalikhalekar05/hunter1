package com.ampinityit.Brahmaent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class VehicleInfoSearchDeatilsActivity extends AppCompatActivity {
    private ImageView serachicon;
    private Company_Details_list_Adapter company_details_list_adapter;
    private ArrayList<Vehicledetail> vehicledetails;
    private RecyclerView recyclerView;
    private String jsonResponce;
    private EditText search,search1;
    private String vehicleno;
    String ade1,latest_value,value1;
    private SharedPreferencesConstants sharedPreferencesConstants;
    private SessionManager sessionManager;
    Button importButton;

    ListView lv;
    DBHelper controllerdb = new DBHelper(this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vehicleinfo_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recycler_view_productlist);
        lv = (ListView) findViewById(R.id.lstview);
        setTitle("VehicleInfo");
        sessionManager=new SessionManager(VehicleInfoSearchDeatilsActivity.this);
        vehicledetails =new ArrayList<>();

        serachicon=findViewById(R.id.serachicon);

        serachicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),StoreData.class);
                startActivity(i);
                finish();
            }
        });
        /*serachicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                vehicledetails.clear();
                new RegistrationFormAsyncTask().execute();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });*/

        search=findViewById(R.id.search);
        search1=findViewById(R.id.search1);
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search1.getText().clear();
            }
        });

        search1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                latest_value = s.toString();
                if(latest_value.length()!=4){
                    recyclerView.setAdapter(null);
                    vehicledetails.clear();
                }
                else{
                    value1=latest_value;
                    new RegistrationFormAsyncTask().execute();
                }
                /*if(s.length() != 4)
                    recyclerView.setAdapter(null);
                    vehicledetails.clear();
                    new RegistrationFormAsyncTask().execute();*/


            }

        });


    }

    public class RegistrationFormAsyncTask extends AsyncTask<String, Integer, String>
    {

        //ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         /*   dialog = new ProgressDialog(VehicleInfoSearchDeatilsActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCancelable(false);*/

        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
           // dialog.dismiss();
            //vehicledetails.clear();
            ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            /*if((search.getText().length()!=4)&&(search1.getText().length()!=4)){
                recyclerView.setAdapter(null);
                vehicledetails.clear();
            }*//*if(latest_value.length()==4){
                search1.setText("");
            }*/
           // System.out.println("latest_value"+latest_value);
            //ade1= search.getText().toString() + search1.getText().toString();
           // ade1= search.getText().toString() + search1.getText().toString();
            if(latest_value.length()==4){
                search1.setText("");
            }
            if (!cd.isConnectingToInternet()) {


              /* ade1= search.getText().toString() + search1.getText().toString();*/

               //if(search.getText().length()!=4&&)
                /*if(ade1.length() != 4)*/
                    recyclerView.setAdapter(null);
                    vehicledetails.clear();

                //offline
                JSONArray result=controllerdb.getResults(value1);


                try {
                    /*JSONObject root = new JSONObject(Result);

                    JSONArray array = root.getJSONArray("result");*/

                    for (int i = 0; i < result.length(); i++) {

                        JSONObject object = result.getJSONObject(i);

                        Vehicledetail vehicledetail = new Vehicledetail();
                        vehicledetail.setAccountNo(object.getString("AccountNo").trim());
                        vehicledetail.setBRANCHNAME(object.getString("BRANCHNAME").trim());
                        vehicledetail.setAllocation_MAR_APR19(object.getString("Allocation_MAR_APR19").trim());
                        vehicledetail.setTEAMLEADERNAME(object.getString("TEAMLEADERNAME").trim());
                        vehicledetail.setTLMOBNO(object.getString("TLMOBNO").trim());
                        vehicledetail.setVehicleModel(object.getString("VehicleModel").trim());
                        vehicledetail.setRegistrationNo(object.getString("RegistrationNo").trim());
                        vehicledetail.setChassisNo(object.getString("ChassisNo").trim());
                        vehicledetail.setEngineNo(object.getString("EngineNo").trim());
                        vehicledetail.setPOSINCR(object.getString("POSINCR").trim());
                        vehicledetail.setMobileNo(object.getString("MobileNo").trim());
                        vehicledetail.setCustomerAddress(object.getString("CustomerAddress").trim());
                        vehicledetail.setCustomerFullName(object.getString("CustomerFullName").trim());
                        vehicledetail.setVehicleMake(object.getString("VehicleMake").trim());

                        vehicledetail.setBUSREGION(object.getString("BUSREGION").trim());
                        vehicledetail.setRepoList(object.getString("RepoList").trim());
                        vehicledetail.setManufacturingYear(object.getString("ManufacturingYear").trim());
                        vehicledetail.setCollectionDPD(object.getString("CollectionDPD").trim());
                        vehicledetail.setPhoneNo(object.getString("PhoneNo").trim());
                        vehicledetail.setCity(object.getString("City").trim());
                        vehicledetail.setPinCode(object.getString("PinCode").trim());
                        vehicledetail.setComapnyName(object.getString("ComapnyName").trim());
                        vehicledetails.add(vehicledetail);

                        /*ArrayList<String> Id = new ArrayList<String>();


                        Id.add(result1);
                        CustomAdapter ca = new CustomAdapter(VehicleInfoSearchDeatilsActivity.this,Id*//*, Account,Branchname*//*);
                        lv.setAdapter(ca);*/

                        company_details_list_adapter = new Company_Details_list_Adapter(vehicledetails, VehicleInfoSearchDeatilsActivity.this);
                        LinearLayoutManager linearLayoutManager
                                = new LinearLayoutManager(VehicleInfoSearchDeatilsActivity.this, LinearLayoutManager.VERTICAL, false);
                        //code to set adapter to populate list
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(company_details_list_adapter);
                        //cursor.close();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //   [{"UserName":"TW778994435","Password":587448.0,"FullName":"ACHUT ADE","MobileNo":9766327427.0}]
            else {//online
                //if (!(ade1.isEmpty())) {

                if (!(value1.isEmpty())) {
                    if (Result != null)
                        try {
                            JSONObject root = new JSONObject(Result);

                            JSONArray array = root.getJSONArray("Table");

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                Vehicledetail vehicledetail = new Vehicledetail();
                                vehicledetail.setAccountNo(object.getString("AccountNo").trim());
                                vehicledetail.setBRANCHNAME(object.getString("BRANCHNAME").trim());
                                vehicledetail.setAllocation_MAR_APR19(object.getString("Allocation_MAR_APR19").trim());
                                vehicledetail.setTEAMLEADERNAME(object.getString("TEAMLEADERNAME").trim());
                                vehicledetail.setTLMOBNO(object.getString("TLMOBNO").trim());
                                vehicledetail.setVehicleModel(object.getString("VehicleModel").trim());
                                vehicledetail.setRegistrationNo(object.getString("RegistrationNo").trim());
                                vehicledetail.setChassisNo(object.getString("ChassisNo").trim());
                                vehicledetail.setEngineNo(object.getString("EngineNo").trim());
                                vehicledetail.setPOSINCR(object.getString("POSINCR").trim());
                                vehicledetail.setMobileNo(object.getString("MobileNo").trim());
                                vehicledetail.setCustomerAddress(object.getString("CustomerAddress").trim());
                                vehicledetail.setCustomerFullName(object.getString("CustomerFullName").trim());
                                vehicledetail.setVehicleMake(object.getString("VehicleMake").trim());

                                vehicledetail.setBUSREGION(object.getString("BUSREGION").trim());
                                vehicledetail.setRepoList(object.getString("RepoList").trim());
                                vehicledetail.setManufacturingYear(object.getString("ManufacturingYear").trim());
                                vehicledetail.setCollectionDPD(object.getString("CollectionDPD").trim());
                                vehicledetail.setPhoneNo(object.getString("PhoneNo").trim());
                                vehicledetail.setCity(object.getString("City").trim());
                                vehicledetail.setPinCode(object.getString("PinCode").trim());
                                vehicledetail.setComapnyName(object.getString("ComapnyName").trim());
                                vehicledetails.add(vehicledetail);

                                company_details_list_adapter = new Company_Details_list_Adapter(vehicledetails, VehicleInfoSearchDeatilsActivity.this);
                                LinearLayoutManager linearLayoutManager
                                        = new LinearLayoutManager(VehicleInfoSearchDeatilsActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(company_details_list_adapter);
                                company_details_list_adapter.notifyDataSetChanged();

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    else {
                        Toast.makeText(VehicleInfoSearchDeatilsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }//2nd if
                else{
                    recyclerView.setAdapter(null);
                }
            }//1st if

        }

        @Override
        protected String doInBackground(String... args) {

            /*ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            if (!cd.isConnectingToInternet()) {



                    db = controllerdb.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT id FROM  contacts",null);
                    Id.clear();


                    if (cursor.moveToFirst()) {
                        do {
                            Id.add(cursor.getString(cursor.getColumnIndex("id")));
                           // Account.add(cursor.getString(cursor.getColumnIndex("account")));


                        } while (cursor.moveToNext());
                    }

                CustomAdapter ca = new CustomAdapter(VehicleInfoSearchDeatilsActivity.this,Id);
                lv.setAdapter(ca);
                *//*company_details_list_adapter = new Company_Details_list_Adapter(vehicledetails,VehicleInfoSearchDeatilsActivity.this);
                LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(VehicleInfoSearchDeatilsActivity.this, LinearLayoutManager.VERTICAL, false);
                    //code to set adapter to populate list
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(company_details_list_adapter);*//*
                cursor.close();
                }*/


           // else {
                String url = sharedPreferencesConstants.KEY_SP_BASE_URL + "searchdata.asmx";
                String soapactionaddlob = "http://tempuri.org/vehicle_Details";
                String methodname = "vehicle_Details";
                String namespacesaalob = "http://tempuri.org/";
                try {
                   // vehicleno = search.getText().toString() + search1.getText().toString();
                    SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);
                    requestAddlob.addProperty("RegistrationNo", value1);
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
           // }else
            return jsonResponce;
        }



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
                AlertDialog.Builder builder = new AlertDialog.Builder(VehicleInfoSearchDeatilsActivity.this);
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


   /* public class RegistrationFormAsyncTaska extends AsyncTask<String, Integer, String>
    {

        //ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         *//*   dialog = new ProgressDialog(VehicleInfoSearchDeatilsActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCancelable(false);*//*

        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
            // dialog.dismiss();
            vehicledetails.clear();
            ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            if (!cd.isConnectingToInternet()) {

                controllerdb.getResults();
                try {
                    JSONObject root = new JSONObject(Result);

                    JSONArray array = root.getJSONArray("Table");

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        Vehicledetail vehicledetail = new Vehicledetail();
                        vehicledetail.setAccountNo(object.getString("AccountNo").trim());
                        vehicledetail.setBRANCHNAME(object.getString("BRANCHNAME").trim());
                        vehicledetail.setAllocation_MAR_APR19(object.getString("Allocation_MAR_APR19").trim());
                        vehicledetail.setTEAMLEADERNAME(object.getString("TEAMLEADERNAME").trim());
                        vehicledetail.setTLMOBNO(object.getString("TLMOBNO").trim());
                        vehicledetail.setVehicleModel(object.getString("VehicleModel").trim());
                        vehicledetail.setRegistrationNo(object.getString("RegistrationNo").trim());
                        vehicledetail.setChassisNo(object.getString("ChassisNo").trim());
                        vehicledetail.setEngineNo(object.getString("EngineNo").trim());
                        vehicledetail.setPOSINCR(object.getString("POSINCR").trim());
                        vehicledetail.setMobileNo(object.getString("MobileNo").trim());
                        vehicledetail.setCustomerAddress(object.getString("CustomerAddress").trim());
                        vehicledetail.setCustomerFullName(object.getString("CustomerFullName").trim());
                        vehicledetail.setVehicleMake(object.getString("VehicleMake").trim());

                        vehicledetail.setBUSREGION(object.getString("BUSREGION").trim());
                        vehicledetail.setRepoList(object.getString("RepoList").trim());
                        vehicledetail.setManufacturingYear(object.getString("ManufacturingYear").trim());
                        vehicledetail.setCollectionDPD(object.getString("CollectionDPD").trim());
                        vehicledetail.setPhoneNo(object.getString("PhoneNo").trim());
                        vehicledetail.setCity(object.getString("City").trim());
                        vehicledetail.setPinCode(object.getString("PinCode").trim());
                        vehicledetail.setComapnyName(object.getString("ComapnyName").trim());
                        vehicledetails.add(vehicledetail);


                        company_details_list_adapter = new Company_Details_list_Adapter(vehicledetails, VehicleInfoSearchDeatilsActivity.this);
                        LinearLayoutManager linearLayoutManager
                                = new LinearLayoutManager(VehicleInfoSearchDeatilsActivity.this, LinearLayoutManager.VERTICAL, false);
                        //code to set adapter to populate list
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(company_details_list_adapter);
                        //cursor.close();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected String doInBackground(String... args) {

            *//*ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            if (!cd.isConnectingToInternet()) {



                    db = controllerdb.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT id FROM  contacts",null);
                    Id.clear();


                    if (cursor.moveToFirst()) {
                        do {
                            Id.add(cursor.getString(cursor.getColumnIndex("id")));
                           // Account.add(cursor.getString(cursor.getColumnIndex("account")));


                        } while (cursor.moveToNext());
                    }

                CustomAdapter ca = new CustomAdapter(VehicleInfoSearchDeatilsActivity.this,Id);
                lv.setAdapter(ca);
                *//**//*company_details_list_adapter = new Company_Details_list_Adapter(vehicledetails,VehicleInfoSearchDeatilsActivity.this);
                LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(VehicleInfoSearchDeatilsActivity.this, LinearLayoutManager.VERTICAL, false);
                    //code to set adapter to populate list
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(company_details_list_adapter);*//**//*
                cursor.close();
                }*//*


            // else {
            String url = sharedPreferencesConstants.KEY_SP_BASE_URL + "searchdata.asmx";
            String soapactionaddlob = "http://tempuri.org/vehicle_Details";
            String methodname = "vehicle_Details";
            String namespacesaalob = "http://tempuri.org/";
            try {
                vehicleno = search.getText().toString() + search1.getText().toString();
                SoapObject requestAddlob = new SoapObject(namespacesaalob, methodname);
                requestAddlob.addProperty("RegistrationNo", vehicleno);
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
            // }else
            return jsonResponce;
        }
*/


  //  }

}
