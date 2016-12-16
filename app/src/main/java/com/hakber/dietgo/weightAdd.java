package com.hakber.dietgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class weightAdd extends AppCompatActivity {
    DBHelper dbHelper;
    Button addWeightButton;
    EditText weightText;
    TextView alertText;
    Calendar myCalendar;
    String currentDate;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        user_id = preferences.getInt("user_id", -1);
        setSupportActionBar(toolbar);
        addWeightButton=(Button) findViewById(R.id.weightAddButton);
        alertText=(TextView) findViewById(R.id.alertText);
        weightText=(EditText) findViewById(R.id.weightText);
        myCalendar= Calendar.getInstance();
        currentDate=String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                .get(Calendar.YEAR));
        addWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                float weight=Float.parseFloat(String.valueOf(weightText.getText()));

                if(weight>0) {

                    DBHandler.insertWeight(weight,user_id,currentDate);


                    Intent i = new Intent(weightAdd.this, weight.class);
                    startActivity(i);
                }
                else{
                    alertText.setText("Lütfen geçerli bir kilo giriniz");
                }

            }

        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

}
