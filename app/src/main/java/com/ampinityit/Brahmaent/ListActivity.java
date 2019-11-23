package com.ampinityit.Brahmaent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    DBHelper controllerdb = new DBHelper(this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Account = new ArrayList<String>();
    private ArrayList<String> Branchname = new ArrayList<String>();

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.lstview);

    }
    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }
    private void displayData() {
        db = controllerdb.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,account,branchname FROM  contacts where id=1",null);
        Id.clear();
        Account.clear();
        Branchname.clear();

        if (cursor.moveToFirst()) {
            do {
                Id.add(cursor.getString(cursor.getColumnIndex("id")));
                Account.add(cursor.getString(cursor.getColumnIndex("account")));
                Branchname.add(cursor.getString(cursor.getColumnIndex("branchname")));

            } while (cursor.moveToNext());
        }
        CustomAdapter ca = new CustomAdapter(ListActivity.this,Id/*, Account,Branchname*/);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}
