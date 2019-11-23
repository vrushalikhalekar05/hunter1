package com.ampinityit.Brahmaent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.ampinityit.Brahmaent.StoreData.arrayLenght;
import static java.sql.Types.NULL;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBNamed.db";
    public static final String CONTACTS_TABLE_NAME = "contacts_v";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "account";
    public static final String CONTACTS_COLUMN_EMAIL = "branchname";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts_vv " +
                        "(ID integer primary key,AccountNo text,BRANCHNAME text,BUSREGION text,Allocation_MAR_APR19 text,TEAMLEADERNAME text,TLMOBNO text,RepoList text,CustomerFullName text,VehicleMake text,VehicleModel text,RegistrationNo text,ManufacturingYear text,ChassisNo text,EngineNo text,POSINCR text,CollectionDPD text,MobileNo text,PhoneNo text,CustomerAddress text,City text,PinCode text,ComapnyName text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts_vv");
        onCreate(db);
    }

    public boolean insertContact (String AccountNo,String BRANCHNAME,String BUSREGION,String Allocation_MAR_APR19,String TEAMLEADERNAME,String TLMOBNO,String RepoList,String CustomerFullName,String VehicleMake,String VehicleModel,String RegistrationNo,String ManufacturingYear,String ChassisNo,String EngineNo,String POSINCR,String CollectionDPD,String MobileNo,String PhoneNo,String CustomerAddress,String City,String PinCode,String ComapnyName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //sqlite3_exec(db, "PRAGMA cache_size=10000", NULL, NULL,&sErrMsg);
        db.beginTransaction();
        ContentValues contentValues = new ContentValues(1);
        //Object[] values = new Object[10000];
        //StringBuilder valuesBuilder = new StringBuilder();
       // for (int i = 0; i < 1300; i++) {
       // contentValues.put("ID", ID);
            contentValues.put("AccountNo", AccountNo);
            contentValues.put("BRANCHNAME", BRANCHNAME);
            contentValues.put("BUSREGION", BUSREGION);
            contentValues.put("Allocation_MAR_APR19", Allocation_MAR_APR19);
            contentValues.put("TEAMLEADERNAME", TEAMLEADERNAME);
            contentValues.put("TLMOBNO", TLMOBNO);
            contentValues.put("RepoList", RepoList);
            contentValues.put("CustomerFullName", CustomerFullName);
            contentValues.put("VehicleMake", VehicleMake);
            contentValues.put("VehicleModel", VehicleModel);
            contentValues.put("RegistrationNo", RegistrationNo);
            contentValues.put("ManufacturingYear", ManufacturingYear);
            contentValues.put("ChassisNo", ChassisNo);
            contentValues.put("EngineNo", EngineNo);
            contentValues.put("POSINCR", POSINCR);
            contentValues.put("CollectionDPD", CollectionDPD);
            contentValues.put("MobileNo", MobileNo);
            contentValues.put("PhoneNo", PhoneNo);
            contentValues.put("CustomerAddress", CustomerAddress);
            contentValues.put("City", City);
            contentValues.put("PinCode", PinCode);
            contentValues.put("ComapnyName", ComapnyName);

            db.insert("contacts_vv", null, contentValues);

        //}
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    public void getData() {
        /*SQLiteDatabase db = this.getReadableDatabase();
       // Cursor res =  db.rawQuery( "select * from contacts_vv", null );
        //Cursor res =  db.rawQuery( "select RegistrationNo from contacts_vv where substr(RegistrationNo,-4) = '2545'", null );
        Cursor res =  db.rawQuery( "select RegistrationNo from contacts_vv where substr(RegistrationNo,-4) = '1450'", null );


        Cursor res1 =  db.rawQuery( "select RegistrationNo from contacts_vv", null );

        //res.toString();

     //  String str1 = res.getString(res.getColumnIndex("RegistrationNo"));
        String str2 = res1.getString(res.getColumnIndex("RegistrationNo"));
     //   System.out.println(str1);
        //System.out.println(str2);
//return res;*/
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts_vv", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public void deleteContact () {
        SQLiteDatabase db = this.getWritableDatabase();



        //db.execSQL("delete * from"+ TABLE_NAME);
        db.execSQL("delete from contacts_vv");
        db.close();
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts_vv", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("ID")));
            res.moveToNext();
        }
        return array_list;
    }
    public JSONArray getResults(String ID )
    {

        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor =  db.rawQuery( "select RegistrationNo from contacts_v where RIGHT(RegistrationNo,4) like  '"+ID+"'", null );
       //Cursor cursor =  db.rawQuery( "select * from contacts_v where substr(RegistrationNo,-4) = '"+ID+"'", null );
        //Cursor cursor =  db.rawQuery( "select RegistrationNo from " + "contacts_v " + "where instr(RegistrationNo,"+ID+")", null );
        //Cursor cursor =  db.rawQuery( "select * from " + "contacts_v "+"where RegistrationNo LIKE "+"'%"+3134+"'", null );
  //Cursor cursor =  db.rawQuery( "select RegistrationNo from " + "contacts_v " + "where substr(RegistrationNo,-4) LIKE "+"'"+"%"+ID+"%"+"'", null );
      Cursor cursor =  db.rawQuery( "select distinct * from " + "contacts_vv "+"where substr(RegistrationNo,-4) = '"+ID+"'", null );
        JSONArray resultSet     = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
      /*  Log.d("TAG_NAME", resultSet.toString() );*/
        System.out.println("resultset"+resultSet);
        return resultSet;
    }
    public void dropDb() {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase(); // helper is object extends SQLiteOpenHelper
        //db.delete(DBHelper.TAB_USERS, null, null);
        //db.delete(DBHelper.TAB_USERS_GROUP, null, null);
        //SQLiteDatabase db=new SQLiteDatabase();
        db.execSQL("DROP TABLE IF EXISTS contacts_vv");
        onCreate(db);

    }
    /*public ArrayList<Vehicledetail> listContacts(){
        String sql = "select * from " + "c";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Vehicledetail> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phno = cursor.getString(2);
                storeContacts.add(new Contacts(id, name, phno));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }*/

    public boolean hasObject(String id) {
        SQLiteDatabase db = getWritableDatabase();

        String selectString = "SELECT RegistrationNo FROM " + "contacts_vv"  + " WHERE " + "RegistrationNo" + " =?";


        Cursor cursor = db.rawQuery(selectString, new String[] {id});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
        //  Log.d(TAG, String.format("%d records found", count));
            Log.d("MyApp",String.format("%d records found", count));
            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }
    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "contacts_vv");
        db.close();
        return count;
    }
    /*public static ArrayList<String> getAlreadyProcessedPhotos(Context context, ArrayList<String> photos, SQLiteDatabase db)
    {
        ArrayList<String> notAlreadyProcessedPhotos = new ArrayList<>();
        ArrayList<String> preProc = new ArrayList()

        for (String item: photos) {
            preProc.add("'" + item + "'");
        }
        String inClause = TextUtils.join(",", preProc);

        String searchQuery = "SELECT " + DatabaseHelper.PATH_COLUMN + "FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.PATH_COLUMN + "NOT IN (" +inClause + ")";
        Cursor cursor = photosDb.rawQuery(searchQuery);

        while(cursor.moveToNext())
        {
            notAlreadyProcessedPhotos.add(cursor.getString(0);
        }

        return notAlreadyProcessedPhotos;
    }

*/
public int search(List<String> regno){
    int hash =  regno.hashCode();
   return hash;

}
}

