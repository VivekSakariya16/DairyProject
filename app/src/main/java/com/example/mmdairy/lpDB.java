package com.example.mmdairy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class lpDB extends SQLiteOpenHelper {
    static String DBID1="IDL";
    static String DBID2="IDP";
    static String DBLitre="Litre";
    static String DBPrice="Price";
    static String DBTABLE1="LitreDATA";
    static String DBTABLE2="PriceDATA";
    public lpDB(@Nullable Context context) {
        super(context, "LPDairy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE "+DBTABLE1+" ("+DBID1+" INTEGER Primary key AUTOINCREMENT, "+DBLitre+" TEXT)";
        db.execSQL(query1);
        String query2 = "CREATE TABLE "+DBTABLE2+" ("+DBID2+" INTEGER Primary key AUTOINCREMENT, "+DBPrice+" TEXT)";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addLitre(String litre){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBLitre,litre);

        long result = db.insert(DBTABLE1,null,values);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }
    public boolean addPrice(String price){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBPrice,price);

        long result = db.insert(DBTABLE2,null,values);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }
    public boolean deleteLitre(String Litre){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+DBTABLE1+" where "+DBLitre+"=?",new String[]{Litre});
        if(cursor.getCount()>0) {
            long result = db.delete(DBTABLE1, DBLitre+"=?", new String[]{Litre});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public boolean deletePrice(String Price){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+DBTABLE2+" where "+DBPrice+"=?",new String[]{Price});
        if(cursor.getCount()>0) {
            long result = db.delete(DBTABLE2, DBPrice+"=?", new String[]{Price});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public ArrayList<String> getLitre(){
        ArrayList<String> x = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String[] column={lpDB.DBLitre};
        Cursor c = db.query(DBTABLE1,column,null,null,null,null,null);
        while (c.moveToNext()){
            int m = c.getColumnIndex(lpDB.DBLitre);
            x.add(c.getString(m));
        }
        return x;
    }
    public ArrayList<String> getPrice(){
        ArrayList<String> x = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String[] column={lpDB.DBPrice};
        Cursor c = db.query(DBTABLE2,column,null,null,null,null,null);
        while (c.moveToNext()){
            int m = c.getColumnIndex(lpDB.DBPrice);
            x.add(c.getString(m));
        }
        return x;
    }
}
