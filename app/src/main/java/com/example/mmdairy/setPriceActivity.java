package com.example.mmdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.EditText;



public class setPriceActivity extends AppCompatActivity{
    EditText setLitre;
    EditText setPrice;
    TextView SETLITRE;
    TextView SETPRICE;
    EditText litreDelete;
    EditText priceDelete;
    TextView DELETELITRE;
    TextView DELETEPRICE;
    TextView showLitre;
    TextView showPrice;
    lpDB lpDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_price);

        setLitre=findViewById(R.id.setLitre);
        setPrice=findViewById(R.id.setPrice);
        SETLITRE = findViewById(R.id.ADDLITRE);
        SETPRICE = findViewById(R.id.ADDPRICE);
        litreDelete=findViewById(R.id.deleteLitre);
        priceDelete=findViewById(R.id.deletePrice);
        DELETELITRE=findViewById(R.id.DELETELITRE);
        DELETEPRICE=findViewById(R.id.DELETEPRICE);
        showLitre=findViewById(R.id.viewSetLitre);
        showPrice=findViewById(R.id.viewSetPrice);

        lpDB=new lpDB(this);
        SETLITRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String litreString = setLitre.getText().toString();
                if(litreString.isEmpty()||litreString.equals("0")){
                    setLitre.setError(getString(R.string.sp_er1));
                }else {
                    lpDB.addLitre(litreString);
                    StringBuffer showlitre =new StringBuffer();
                    showlitre.append(getString(R.string.spl));
                    int i=0;
                    while (i<lpDB.getLitre().size()) {
                        showlitre.append(lpDB.getLitre().get(i)).append(",");
                        i++;
                    }
                    showLitre.setText(showlitre.toString());
                    setLitre.setText("");
                    closeKeyboard();
                }
            }
        });

        SETPRICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceString = setPrice.getText().toString();
                if(priceString.isEmpty()||priceString.equals("0")){
                    setPrice.setError(getString(R.string.sp_er2));
                }else {
                    lpDB.addPrice(priceString);
                    StringBuffer showprice =new StringBuffer();
                    showprice.append(getString(R.string.spp));
                    int i=0;
                    while (i<lpDB.getPrice().size()) {
                        showprice.append(lpDB.getPrice().get(i)).append(",");
                        i++;
                    }
                    showPrice.setText(showprice.toString());
                    setPrice.setText("");
                    closeKeyboard();
                }
            }
        });

        DELETELITRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String litre = litreDelete.getText().toString();
                if (litre.isEmpty() || litre.equals("0")) {
                    litreDelete.setError(getString(R.string.sp_er1));
                }else {
                    lpDB.deleteLitre(litre);
                    StringBuffer showlitre =new StringBuffer();
                    showlitre.append(getString(R.string.spl));
                    int i=0;
                    while (i<lpDB.getLitre().size()) {
                        showlitre.append(lpDB.getLitre().get(i)).append(",");
                        i++;
                    }
                    showLitre.setText(showlitre.toString());
                    litreDelete.setText("");
                    closeKeyboard();
                }
            }
        });

        DELETEPRICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = priceDelete.getText().toString();
                if(price.isEmpty()||price.equals("0")){
                    priceDelete.setError(getString(R.string.sp_er2));
                }else {
                    lpDB.deletePrice(price);
                    StringBuffer showprice =new StringBuffer();
                    showprice.append(getString(R.string.spp));
                    int i=0;
                    while (i<lpDB.getPrice().size()) {
                        showprice.append(lpDB.getPrice().get(i)).append(",");
                        i++;
                    }
                    showPrice.setText(showprice.toString());
                    priceDelete.setText("");
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