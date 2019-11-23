package com.ampinityit.Brahmaent;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InfomationActivity extends AppCompatActivity {
    TextView FinanaceName,Finanace_Contact,ContactNumber,Branch,Agreementno,Bkt,VehicleMaker,VehicleModel,ManufacturingYear,
            EngineNo,ChasisNo,Region,Posincr,Customername,CustomerAdd,Share;
    Toolbar toolbar;
    String FinanaceNameSt,Finanace_ContactSt,ContactNumberSt,BranchSt,AgreementnoSt,BktSt,VehicleMakerSt,VehicleModelSt,ManufacturingYearSt,
            EngineNoSt,ChasisNoSt,RegionSt,PosincrSt,CustomernameSt,CustomerAddSt,Registrationst;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lay_information_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager=new SessionManager(InfomationActivity.this);
        FinanaceName=findViewById(R.id.FinanaceName);
        Finanace_Contact=findViewById(R.id.Finanace_Contact);
        ContactNumber=findViewById(R.id.ContactNumber);
        Branch=findViewById(R.id.Branch);
        Agreementno=findViewById(R.id.Agreementno);
        Bkt=findViewById(R.id.Bkt);
        VehicleMaker=findViewById(R.id.VehicleMaker);
        VehicleModel=findViewById(R.id.VehicleModel);
        ManufacturingYear=findViewById(R.id.ManufacturingYear);
        EngineNo=findViewById(R.id.EngineNo);
        ChasisNo=findViewById(R.id.ChasisNo);
        Region=findViewById(R.id.Region);
        Posincr=findViewById(R.id.Posincr);

        Customername=findViewById(R.id.Customername);
        CustomerAdd=findViewById(R.id.CustomerAdd);

        Intent intent=getIntent();
        FinanaceNameSt=intent.getStringExtra("ComapnyName");
        Finanace_ContactSt=intent.getStringExtra("TEAMLEADERNAME");
        ContactNumberSt=intent.getStringExtra("TLMOBNO");
        AgreementnoSt=intent.getStringExtra("AccountNo");
        BranchSt=intent.getStringExtra("BRANCHNAME");
        BktSt=intent.getStringExtra("CollectionDPD");
        VehicleMakerSt=intent.getStringExtra("VehicleMake");
        VehicleModelSt=intent.getStringExtra("VehicleModel");
        EngineNoSt=intent.getStringExtra("EngineNo");
        PosincrSt=intent.getStringExtra("POSINCR");
        CustomerAddSt=intent.getStringExtra("CustomerAddress");
        CustomernameSt=intent.getStringExtra("CustomerFullName");
        ChasisNoSt=intent.getStringExtra("ChassisNo");
        Registrationst=intent.getStringExtra("RegistrationNo");
        RegionSt=intent.getStringExtra("BUSREGION");

        setTitle(Registrationst);


        FinanaceName.setText(FinanaceNameSt);
        Finanace_Contact.setText(Finanace_ContactSt);
        ContactNumber.setText(ContactNumberSt);
        Agreementno.setText(AgreementnoSt);
        Bkt.setText(BktSt);
        VehicleMaker.setText(VehicleMakerSt);
        VehicleModel.setText(VehicleModelSt);
        EngineNo.setText(EngineNoSt);
        ChasisNo.setText(ChasisNoSt);
        ManufacturingYear.setText(BktSt);
        Region.setText(BktSt);
        Posincr.setText(PosincrSt);
        Customername.setText(CustomernameSt);
        CustomerAdd.setText(CustomerAddSt);
        Share=findViewById(R.id.Share);
        Branch.setText(BranchSt);
        Region.setText(RegionSt);

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody =FinanaceNameSt+" " +Finanace_ContactSt+""+ContactNumberSt+" "+AgreementnoSt+" "+BranchSt+" " +CustomernameSt
                        +" "+CustomerAddSt+" "+Registrationst;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(InfomationActivity.this);
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
}
