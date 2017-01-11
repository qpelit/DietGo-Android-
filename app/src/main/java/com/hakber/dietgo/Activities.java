package com.hakber.dietgo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activities extends AppCompatActivity {
    private Spinner spinnerActivity;
    float[] array;
    float weight; // user's weight to calculate the result
    TextView textViewCalorieResult;
    EditText activityTimeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        weight = preferences.getFloat("weight", -1);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewCalorieResult=(TextView) findViewById(R.id.textViewCalorieResult);
        spinnerActivity = (Spinner) findViewById(R.id.spinnerActivity);
        activityTimeText=(EditText)findViewById(R.id.activityTimeText);
        array= new float[]{0.08f, 0.12f,0.13f, 0.1f,0.13f,0.12f,0.08f,0.13f,0.12f,0.07f};
        List<String> pgList = new ArrayList<String>();
        pgList.add("Yürüyüş (yavaş)");
        pgList.add("Yürüyüş (hızlı)");
        pgList.add("Koşu");
        pgList.add("Basketbol");
        pgList.add("Futbol");
        pgList.add("Kayak");
        pgList.add("Kaykay");
        pgList.add("Yüzme");
        pgList.add("Tenis");
        pgList.add("Masa Tenisi");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, pgList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(dataAdapter);

        findViewById(R.id.activityAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float result;
                int  time = Integer.parseInt(activityTimeText.getText().toString());
                result=array[(int)spinnerActivity.getSelectedItemId()]*weight*time;
                textViewCalorieResult.setText(String.valueOf(result) + "kcal verildi. ");
               /*
                 int time=0;

                 if(activityTimeText.getText().toString()!="") {
                     time = Integer.parseInt(activityTimeText.getText().toString());

                if(time>0){
                result=array[(int)spinnerActivity.getSelectedItemId()]*weight*time;
                textViewCalorieResult.setText(String.valueOf(result) + "kcal verildi. ");
                }
                }
                else{
                    textViewCalorieResult.setText("En az 1 dakika giriniz.");

                }
                */
            }
        });



    }

}
