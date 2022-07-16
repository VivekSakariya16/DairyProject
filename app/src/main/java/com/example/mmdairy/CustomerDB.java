package com.example.mmdairy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CustomerDB extends SQLiteOpenHelper {
    static String DBID="ID";
    static String DBNAME="Name";
    static String DBMOB="MobileNo";
    static String DBTABLE="DairyDATA";
    public CustomerDB(Context context) {
        super(context, "MMDairy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+DBTABLE+" ("+DBID+" INTEGER Primary key AUTOINCREMENT, "+DBNAME+" TEXT,"+DBMOB+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addCustomer(String name,String mobileNo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBNAME,name);
        values.put(DBMOB,mobileNo);

        long result = db.insert("DairyDATA",null,values);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteCustomer(String id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+DBTABLE+" where "+DBID+"=?",new String[]{id});
        if(cursor.getCount()>0) {
            long result = db.delete(DBTABLE, DBID+"=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public String getCustomer(String ids){
        String x = "";
        SQLiteDatabase db = getWritableDatabase();
        String[] column={CustomerDB.DBNAME};
        Cursor c = db.query(DBTABLE,column,DBID+" = "+Integer.parseInt(ids),null,null,null,null);
        while (c.moveToNext()){
            int index = c.getColumnIndex(CustomerDB.DBNAME);
            x+=c.getString(index);
        }
        return x;
    }
    public StringBuffer getAllCustomer(){
        StringBuffer x = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String[] column={CustomerDB.DBID,CustomerDB.DBNAME,CustomerDB.DBMOB};
        Cursor c = db.query(DBTABLE,column,null,null,null,null,null);
        while (c.moveToNext()){
            int m = c.getColumnIndex(CustomerDB.DBID);
            int n = c.getColumnIndex(CustomerDB.DBNAME);
            int o = c.getColumnIndex(CustomerDB.DBMOB);
            x.append("\t\t"+c.getString(m)+"\t\t\t");
            if(c.getString(m).length()==1){
                x.append("\t\t");
            }else if(c.getString(m).length()==2){
                x.append("\t");
            }else {
                x.append("");
            }
            x.append(c.getString(o)+"\t\t\t\t\t"+c.getString(n)+"\n");
        }
        return x;
    }

    public String getMob(String ids){
        StringBuffer x = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String[] column={CustomerDB.DBMOB};
        Cursor c = db.query(DBTABLE,column,DBID+" = "+Integer.parseInt(ids),null,null,null,null);
        while (c.moveToNext()){
            int index = c.getColumnIndex(CustomerDB.DBMOB);
            x.append(c.getString(index));
        }
        return x.toString();
    }

    public String getMobAll() {
        StringBuffer x = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String[] column={CustomerDB.DBMOB};
        Cursor c = db.query(DBTABLE,column,null,null,null,null,null);
        while (c.moveToNext()){
            int index = c.getColumnIndex(CustomerDB.DBMOB);
            x.append(c.getString(index) + " ");
        }
        return x.toString();
    }
}
