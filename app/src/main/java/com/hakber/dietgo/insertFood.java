package com.hakber.dietgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class insertFood extends AppCompatActivity {
    private EditText food_nameText;
    private EditText calorieText;
    private EditText fatText;
    private EditText carboText;
    private EditText proteinText;
    private Button btnInsert;
    private Spinner spinnerType;
    int user_id;
    public static final String ROOT_URL = "http://hakanpelit.pe.hu/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        user_id = preferences.getInt("user_id", -1);
        food_nameText = (EditText) findViewById(R.id.food_name_insert);
        calorieText = (EditText) findViewById(R.id.calorie_insert);
        fatText = (EditText) findViewById(R.id.fat_insert);
        carboText = (EditText) findViewById(R.id.carbo_insert);
        proteinText = (EditText) findViewById(R.id.protein_insert);

        spinnerType = (Spinner) findViewById(R.id.spinner_type_insert);
        List<String> pgList = new ArrayList<String>();
        pgList.add("p");
        pgList.add("g");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, pgList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.btn_food_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertCustomFood(food_nameText.getText().toString(),
                Float.parseFloat(calorieText.getText().toString()),
                Float.parseFloat(fatText.getText().toString()),
                Float.parseFloat(carboText.getText().toString()),
                Float.parseFloat(proteinText.getText().toString()),
                spinnerType.getSelectedItem().toString(),
                user_id*10 // category will be user_id+0 -> "user_id0"

                );


            }
        });
    }
    public  void insertCustomFood(String foodName,float calorie,float fat,float carbo,float protein,String type,int category) {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterFoodAPI api = adapter.create(RegisterFoodAPI.class);

        //Defining the method insertuser of our interface
        api.insertUser(
                //Passing the values by getting it from editTexts
                foodName,
                calorie,
                fat,
                carbo,
                protein,
                type,
                category,

                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        if(output.toString().equals("success")){
                            Intent i = new Intent( insertFood.this,List1.class);
                            startActivity(i);

                        }
                        else{

                            Toast.makeText(insertFood.this,"Beklenmedik Hata",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(insertFood.this,"Baglanılamadı.",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
