package com.example.mmdairy;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class customerActivity extends AppCompatActivity {
    EditText name;
    EditText mobile;
    TextView save;
    CustomerDB customerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobileno);
        save=findViewById(R.id.save);

        customerDB = new CustomerDB(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = name.getText().toString();
                String customerMobile = mobile.getText().toString();

                boolean checkinsertdata=false;
                if(customerName.isEmpty()){
                    name.setError(getString(R.string.c_er1));
                }if(customerMobile.length()!=10){
                    mobile.setError(getString(R.string.c_er2));
                }else {
                    checkinsertdata=customerDB.addCustomer(customerName, customerMobile);
                }
                if(checkinsertdata){
                    Toast.makeText(customerActivity.this, customerName + getString(R.string.ctt), Toast.LENGTH_SHORT).show();
                    name.setText("");
                    mobile.setText("");
                    closeKeyboard();
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