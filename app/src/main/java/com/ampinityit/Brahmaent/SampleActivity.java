package com.ampinityit.Brahmaent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    DBHelper controllerdb = new DBHelper(this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    ListView lv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lv = (ListView) findViewById(R.id.lstview);
        btn=(Button) findViewById(R.id.btnShow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = controllerdb.getReadableDatabase();
                //Cursor cursor = db.rawQuery( "select RegistrationNo from " + "contacts_v " + "where instr(RegistrationNo,"+2345+")",null);
                Cursor cursor = db.rawQuery( "select * from " + "contacts_v "+"where RegistrationNo LIKE "+"'%"+3134+"'",null);
                //Cursor cursor = db.rawQuery( "select RegistrationNo from " + "contacts_v ",null);


                Id.clear();


                if (cursor.moveToFirst()) {
                    do {
                        Id.add(cursor.getString(cursor.getColumnIndex("RegistrationNo")));


                    } while (cursor.moveToNext());
                }

                CustomAdapter ca = new CustomAdapter(SampleActivity.this,Id/*, Account,Branchname*/);
                lv.setAdapter(ca);
                //code to set adapter to populate list
                cursor.close();
            }
        });
    }
    @Override
    protected void onResume() {
        //displayData();
        super.onResume();
    }
    private void displayData() {

    }

}
