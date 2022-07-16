package com.example.mmdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class viewActivity extends AppCompatActivity {
    EditText id;
    TextView name;
    TextView delete;
    TextView showdata;
    CustomerDB customerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        id=findViewById(R.id.getiddelete);
        name=findViewById(R.id.vName);
        delete=findViewById(R.id.delete);
        showdata=findViewById(R.id.showAllDATA);

        customerDB = new CustomerDB(this);
        showData();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdata.setText("");
            }
        });
        id.addTextChangedListener(new TextWatcher() {
            int len;
            @Override
            public void afterTextChanged(Editable s) {
                showdata.setText("");
                if(len==0){
                    name.setText(getString(R.string.c_name));
                }else {
                    String str = id.getText().toString();
                    name.setText(getString(R.string.m_er2)+customerDB.getCustomer(str));
                    closeKeyboard();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                showdata.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = id.getText().toString();
                len = str.length();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                String Id=id.getText().toString();
                alert.setTitle(R.string.vd);
                alert.setMessage(R.string.vdw);
                alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        customerDB.deleteCustomer(Id);
                        id.setText("");
                        name.setText(getString(R.string.c_name));
                        showData();
                        Toast.makeText(viewActivity.this, getString(R.string.de), Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                if(Id.isEmpty()){
                    id.setError(getString(R.string.m_er3));
                }else {
                    alert.show();
                }
            }
        });
    }
    public void showData(){
        StringBuffer data = new StringBuffer();
        data.append("\t\tID\t\t\t\t\tMobile No.\t\t\t\t\t\t\tName\n\n");
        data.append(customerDB.getAllCustomer());
        showdata.setText(data.toString());
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