package com.example.mmdairy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dailyMilkDB extends SQLiteOpenHelper{
    static String DBTable = "DailyDATA";
    static String DBDID = "DATAID";
    static String DBID = "ID";
    static String DBDATE = "DATE";
    static String DBLitre = "Litre";
    static String DBPrice = "Price";

    public dailyMilkDB(Context context) {
        super(context, "DailyDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+DBTable+" ("+DBDID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+DBDATE+" TEXT,"+DBLitre+" DOUBLE,"+DBPrice+" DOUBLE,"+DBID+" INTEGER," +
                "FOREIGN KEY ("+DBID+") REFERENCES DairyDATA("+DBID+"));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addDATA(String id,String date,String litre, String price){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBID,id);
        values.put(DBDATE,date);
        values.put(DBLitre,litre);
        values.put(DBPrice,price);

        long result = db.insert("DailyDATA",null,values);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public String createPDF(String id, String startDate,String m){
        SQLiteDatabase db = getWritableDatabase();

        String[] column={dailyMilkDB.DBDATE,dailyMilkDB.DBLitre,dailyMilkDB.DBPrice};
        Cursor c = db.query(DBTable,column,DBID+" = "+id+" AND "+DBDATE+" LIKE '___"+m+"_____'",null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        Log.e("MZ",startDate);
        Log.e("MZ",m);
        while (c.moveToNext()){
            int dateI = c.getColumnIndex(dailyMilkDB.DBDATE);
            int litreI = c.getColumnIndex(dailyMilkDB.DBLitre);
            int priceI = c.getColumnIndex(dailyMilkDB.DBPrice);
            String date = c.getString(dateI);
            String litre = c.getString(litreI);
            String price = c.getString(priceI);
            buffer.append(date+" "+litre+" "+price+" ");
        }
        return buffer.toString();
    }

    public String getdata(String id,String m){
        SQLiteDatabase db = getWritableDatabase();
        String[] q = new String[]{DBDATE,DBLitre,DBPrice};
        Cursor c = db.query(DBTable,q,DBID+" = "+id+" AND "+DBDATE+" LIKE '___"+m+"_____'",null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        buffer.append("\t\tDate                 Litre   Price      Total\n");
        double total=0;
        while (c.moveToNext()){
            int dateI = c.getColumnIndex(dailyMilkDB.DBDATE);
            int litreI = c.getColumnIndex(dailyMilkDB.DBLitre);
            int priceI = c.getColumnIndex(dailyMilkDB.DBPrice);
            String date = c.getString(dateI);
            String litre = c.getString(litreI);
            String price = c.getString(priceI);
            total+=Double.parseDouble(litre)*Double.parseDouble(price);
            buffer.append("\t\t"+date+"      "+litre+"         "+price+"         "+Double.parseDouble(litre)*Double.parseDouble(price)+"\n");
        }
        buffer.append("\n\t\t Total = "+total);
        return buffer.toString();
    }
}
