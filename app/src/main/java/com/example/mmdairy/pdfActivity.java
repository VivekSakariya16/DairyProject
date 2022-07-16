package com.example.mmdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class pdfActivity extends AppCompatActivity {
    EditText getIdPDF;
    TextView sDate;
    TextView getData;
    TextView getPDF;
    TextView showD;
    String startDate;
    CustomerDB customerDB;
    dailyMilkDB dailyMilkDB;
    final Calendar myCalendar= Calendar.getInstance();

    private String updateLabel(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(myCalendar.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        getIdPDF=findViewById(R.id.getidpdf);
        sDate=findViewById(R.id.sDATE);
        getData=findViewById(R.id.getDATA);
        getPDF=findViewById(R.id.getpdf);
        showD=findViewById(R.id.showDATA);

        dailyMilkDB=new dailyMilkDB(this);
        customerDB = new CustomerDB(this);

        DatePickerDialog.OnDateSetListener dateX =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                startDate=updateLabel();
                sDate.setText(startDate);
            }
        };
        sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showD.setText("");
                closeKeyboard();
                new DatePickerDialog(pdfActivity.this,dateX,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPDF = getIdPDF.getText().toString();
                String startdate = sDate.getText().toString();
                if (idPDF.isEmpty()) {
                    getIdPDF.setError(getString(R.string.m_er3));
                }
                else {
                    closeKeyboard();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("\t\tID : "+idPDF+"  \n\t\tName : "+customerDB.getCustomer(idPDF)+"\n\n");
                    stringBuffer.append(dailyMilkDB.getdata(idPDF,startdate.substring(5,7)));
                    showD.setText(stringBuffer.toString());
                }
            }
        });


        getIdPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPDF=getIdPDF.getText().toString();
            }
        });
        getPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showD.setText("");
                String idPDF=getIdPDF.getText().toString();
                String startdate = sDate.getText().toString();
                if(idPDF.isEmpty()){
                    getIdPDF.setError(getString(R.string.m_er3));
                }else {
                    try {
                        closeKeyboard();
                        generatePDF(idPDF, startDate,startdate.substring(5,7));
                        getIdPDF.setText("");
                        Toast.makeText(pdfActivity.this, getString(R.string.p_er1), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void generatePDF(String id, String StartDate, String mo) throws FileNotFoundException {
        int pageWidth=2100;
        PdfDocument pdfDocument= new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(2100 ,2970,1).create();
        PdfDocument.Page myPage1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas = myPage1.getCanvas();

        myPaint.setStrokeWidth(2);
        canvas.drawLine(100,100,pageWidth-100,100,myPaint);
        canvas.drawLine(100,2870,pageWidth-100,2870,myPaint);
        canvas.drawLine(100,100,100,2870,myPaint);
        canvas.drawLine(pageWidth-100,100,pageWidth-100,2870,myPaint);


        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        myPaint.setTextSize(70);
        canvas.drawText("MM Dairy",pageWidth/2,200,myPaint);
        canvas.drawLine(100,250,pageWidth-100,250,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(50);
        myPaint.setColor(Color.BLACK);
        if(customerDB.getCustomer(id).length()!=0) {
            canvas.drawText("Name: " + customerDB.getCustomer(id), 200, 350, myPaint);
        }else{
            return;
        }

        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("ID : "+id,pageWidth-500,350,myPaint);
        canvas.drawLine(100,400,pageWidth-100,400,myPaint);


        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("Sr. No.",300,500,myPaint);
        canvas.drawText("Date",575,500,myPaint);
        canvas.drawText("Litre",850,500,myPaint);
        canvas.drawText("Price",1050,500,myPaint);
        canvas.drawText("Total",1250,500,myPaint);

        canvas.drawLine(200,425,1350,425,myPaint);
        canvas.drawLine(200,425,200,550,myPaint);
        canvas.drawLine(400,425,400,550,myPaint);
        canvas.drawLine(750,425,750,550,myPaint);
        canvas.drawLine(950,425,950,550,myPaint);
        canvas.drawLine(1150,425,1150,550,myPaint);
        canvas.drawLine(1350,425,1350,550,myPaint);
        canvas.drawLine(200,550,1350,550,myPaint);

        myPaint.setTextSize(50);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        String result = dailyMilkDB.createPDF(id,StartDate,mo);
        ArrayList<String>str =new ArrayList<>();
        StringBuffer s = new StringBuffer();
        s.append(result);
        Log.e("MX",s.toString());
        while(s.length()!=0) {
            String sm = s.substring(0, s.indexOf(" "));
            str.add(sm);
            s.delete(0, s.indexOf(" ") + 1);
        }

        int y = 600;
        int startY=550;
        double totalx=0;
        int x=0;
        int i=0;

        while(i!=str.size()/3 && i<31) {
            String a =str.get(x);
            String b =str.get(x+1);
            String c=str.get(x+2);

            Log.e("MX",a);
            Log.e("MX",b);
            Log.e("MX",c);
            x+=3;

            canvas.drawLine(200,startY,200,startY+70,myPaint);
            canvas.drawLine(400,startY,400,startY+70,myPaint);
            canvas.drawLine(750,startY,750,startY+70,myPaint);
            canvas.drawLine(950,startY,950,startY+70,myPaint);
            canvas.drawLine(1150,startY,1150,startY+70,myPaint);
            canvas.drawLine(1350,startY,1350,startY+70,myPaint);

            canvas.drawText((i+1)+".", 300, y, myPaint);
            canvas.drawText(a, 600, y, myPaint);
            canvas.drawText(b, 850, y, myPaint);
            canvas.drawText(c, 1050, y, myPaint);
            double m = Double.parseDouble(b)*Double.parseDouble(c);
            Log.e("MX",String.valueOf(m));
            canvas.drawText(String.valueOf(m), 1250, y, myPaint);

            totalx+=m;
            Log.e("MX",String.valueOf(totalx));
            y+=70;
            startY+=70;

            i++;
        }


        Log.e("MX",String.valueOf(totalx));
        canvas.drawLine(200,startY,1350,startY,myPaint);
        canvas.drawLine(950,startY,950,startY+100,myPaint);
        canvas.drawLine(1350,startY,1350,startY+100,myPaint);
        canvas.drawLine(950,startY+100,1350,startY+100,myPaint);

        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("Total",1050,startY+65,myPaint);
        canvas.drawText(":",1150,startY+65,myPaint);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(totalx),1250,startY+65,myPaint);
        Log.e("MX",String.valueOf(totalx));


        pdfDocument.finishPage(myPage1);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(path,id+"_"+StartDate.substring(5,7)+"_MMDairy.pdf");
        Log.e("xy","pdf");
        OutputStream outputStream = new FileOutputStream(file);

//        Log.e("xy","pdfd");
        try {
            pdfDocument.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
    public void closeKeyboard()
    {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}