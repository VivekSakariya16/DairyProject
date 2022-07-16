package com.example.mmdairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class smsActivity extends AppCompatActivity {
    EditText write;
    TextView send;
    CustomerDB customerDB= new CustomerDB(this);
    SmsManager smsManager=SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        write=findViewById(R.id.writeMessage);
        send = findViewById(R.id.send);
        if(ActivityCompat.checkSelfPermission(smsActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_DENIED||
                ActivityCompat.checkSelfPermission(smsActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(smsActivity.this,new String[]{Manifest.permission.SEND_SMS},44);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = write.getText().toString();
                if(s.isEmpty()){
                    write.setError(getString(R.string.sm_er));
                }else{
                    closeKeyboard();
                    ArrayList<String> str =new ArrayList<>();
                    StringBuffer sb = new StringBuffer();
                    sb.append(customerDB.getMobAll());
                    while(sb.length()!=0) {
                        String sm = sb.substring(0, sb.indexOf(" "));
                        str.add(sm);
                        Log.e("Bo",sm);
                        sb.delete(0, sb.indexOf(" ") + 1);
                    }
                    int i=0;
                    Log.e("Bo",String.valueOf(str.size()));
                    while(i<str.size()) {
                        Log.e("Bo",s);
                        String a = str.get(i);
                        Log.e("Bo",a);
                        smsManager.sendTextMessage(a, null, s, null, null);
                        i++;
                    }
                    write.setText("");
                    Toast.makeText(smsActivity.this, getString(R.string.smt), Toast.LENGTH_SHORT).show();
                }
            }
        });
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