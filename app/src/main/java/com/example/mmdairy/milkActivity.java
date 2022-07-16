package com.example.mmdairy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

public class milkActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText idD;
    TextView setname;
    Spinner litre;
    Spinner price;
    TextView submit;
    TextView setdate;
    String date;
    String resultOfLitre;
    String resultOfPrice;
    ArrayAdapter litreAdapter;
    ArrayAdapter priceAdapter;
    dailyMilkDB dailyMilkDB;
    lpDB lpDB;
    ArrayList<String> litreDATA=new ArrayList<>();
    ArrayList<String> priceDATA = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailymilk);

        Log.e("hv","he");
        if(ActivityCompat.checkSelfPermission(milkActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(milkActivity.this,new String[]{Manifest.permission.SEND_SMS},44);
        }
        idD=findViewById(R.id.getid);
        setname=findViewById(R.id.setname);
        litre=findViewById(R.id.litre);
        price=findViewById(R.id.price);
        submit=findViewById(R.id.submit);
        setdate=findViewById(R.id.Date);

        date = (String) android.text.format.DateFormat.format("dd-MM-yyyy", new Date());
        setdate.setText(date);

        lpDB=new lpDB(this);
        litreDATA= (ArrayList<String>) lpDB.getLitre().clone();
        priceDATA= (ArrayList<String>) lpDB.getPrice().clone();


        litreAdapter=new ArrayAdapter(milkActivity.this, android.R.layout.simple_spinner_item, litreDATA);
        priceAdapter=new ArrayAdapter(milkActivity.this, android.R.layout.simple_spinner_item, priceDATA);
        Log.e("hv","x");
        litreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        litre.setAdapter(litreAdapter);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price.setAdapter(priceAdapter);

        dailyMilkDB=new dailyMilkDB(this);
        CustomerDB customerDB= new CustomerDB(this);

        idD.addTextChangedListener(new TextWatcher() {
            int len;
            @Override
            public void afterTextChanged(Editable s) {
                if(len==0){
                    setname.setText(getString(R.string.c_name));
                }else {
                    String str = idD.getText().toString();
                    setname.setText(getString(R.string.m_er2)+customerDB.getCustomer(str));
                    closeKeyboard();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = idD.getText().toString();
                len = str.length();
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkinsertdata = false;
                String idm=idD.getText().toString();
                if(idm.isEmpty()){
                    idD.setError(getString(R.string.m_er3));
                }else {
                    checkinsertdata = dailyMilkDB.addDATA(idm, date, litreDATA.get(litre.getSelectedItemPosition()), priceDATA.get(price.getSelectedItemPosition()));
                    SmsManager smsManager=SmsManager.getDefault();
                    Log.e("MOB",customerDB.getMob(idm));
                    smsManager.sendTextMessage(customerDB.getMob(idm),null,"ID = "+idm+"_"+litreDATA.get(litre.getSelectedItemPosition())+"L_"+priceDATA.get(price.getSelectedItemPosition())+"Rs.="+Double.parseDouble(litreDATA.get(litre.getSelectedItemPosition()))*Double.parseDouble(priceDATA.get(price.getSelectedItemPosition()))+"Rs.",null,null);
                    Toast.makeText(milkActivity.this, customerDB.getCustomer(idm)+"_"+litreDATA.get(litre.getSelectedItemPosition())+"L_"+priceDATA.get(price.getSelectedItemPosition())+"Rs.", Toast.LENGTH_SHORT).show();
                }
                if(checkinsertdata){
                    idD.setText("");
                    setname.setText("");
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        litre = (Spinner)parent;
        price = (Spinner)parent;
        if(litre.getId() == R.id.litre) {
            resultOfLitre = litreDATA.get(position);
        }
        if(price.getId() == R.id.price) {
            resultOfPrice = priceDATA.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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