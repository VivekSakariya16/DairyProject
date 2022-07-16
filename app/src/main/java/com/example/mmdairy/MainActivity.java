package com.example.mmdairy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    ImageView dailymilk;
    ImageView newcustomer;
    ImageView viewpdf;
    ImageView setprice;
    ImageView sms;
    ImageView view;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},44);
        }
        dailymilk = findViewById(R.id.dailymilk);
        newcustomer = findViewById(R.id.customer);
        viewpdf = findViewById(R.id.viewpdf);
        setprice = findViewById(R.id.setprice);
        sms = findViewById(R.id.sms);
        view = findViewById(R.id.view);

        dailymilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,milkActivity.class);
                startActivity(i);
            }
        });

        newcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, customerActivity.class);
                startActivity(i);
            }
        });

        viewpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, pdfActivity.class);
                startActivity(i);
            }
        });

        setprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, setPriceActivity.class);
                startActivity(i);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, smsActivity.class);
                startActivity(i);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, viewActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alert);
        alert.setMessage(R.string.exit);
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
            }
        });
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_en){
            LocaleHelper.setLocale(this,"en");
            return true;
        }
        if(id==R.id.action_guj){
            LocaleHelper.setLocale(this,"gu");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
