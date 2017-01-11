package com.hakber.dietgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class welcomeScreen extends AppCompatActivity {
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private float initialX;
    private Spinner spinnerGender;
    private Spinner spinnerInfo;

    private EditText user_name;
    private EditText password;
    private EditText weight;
    private EditText height;
    private EditText birthYear;
    private Button btnRegister;
    public static final String ROOT_URL = "http://hakanpelit.pe.hu/";
    int spinnerInfoPos;
    float bmhMultiply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        mContext = this;
        spinnerInfoPos=0;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(6000);
        mViewFlipper.startFlipping();
        user_name= (EditText) findViewById(R.id.user_name);
        password= (EditText) findViewById(R.id.password);
        birthYear= (EditText) findViewById(R.id.birthYear);
        height= (EditText) findViewById(R.id.height);
        weight= (EditText) findViewById(R.id.weight);

        btnRegister= (Button) findViewById(R.id.btnRegister);

        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        List<String> genderList = new ArrayList<String>();
        genderList.add("Kadın");
        genderList.add("Erkek");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genderList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(dataAdapter);



        spinnerInfo = (Spinner) findViewById(R.id.spinnerInfo);
        List<String> infoList = new ArrayList<String>();
        infoList.add("Haftada hiç egzersiz yapmıyorum.");
        infoList.add("Haftada 1 ya da 2 kez hafif egzersizler yapıyorum.");
        infoList.add("Haftada 4-5 gün spor yapıyorum");
        infoList.add("Hergün çok ağır idman yapıyorum.");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, infoList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInfo.setAdapter(dataAdapter2);

        spinnerInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
               spinnerInfoPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            bmhMultiply=infoFunction(spinnerInfoPos);
                    //inserting user by sending user infos
                    insertUser(view, user_name.getText().toString(), password.getText().toString(), spinnerGender.getSelectedItem().toString(), Integer.parseInt(birthYear.getText().toString()), Integer.parseInt(height.getText().toString()),Float.parseFloat(weight.getText().toString()),bmhMultiply);


            }
        });
        findViewById(R.id.btnLinkToLoginScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent( welcomeScreen.this,welcome_screen2.class);
                startActivity(i);


            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        return false;
    }


    public void successfullLogin(){
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("isLogged", true);
        editor.commit();
        Intent i = new Intent( welcomeScreen.this,MainActivity.class);
        startActivity(i);

    }

public float infoFunction(int pos){
    float inMul;

    switch (pos) {
        case 0 :
           inMul=1.2f;
            break;

        case 1 :
            inMul=1.375f;
            break;

        case 2 :
            inMul=1.55f;
            break;

        default :
            inMul=1.725f;

            break;
    }
    return inMul;
}
    public void insertUser(final View view, String user_name, String password, String gender, int birthYear, int height,float weight,float bmhMul) {

        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterUserAPI api = adapter.create(RegisterUserAPI.class);

        //Defining the method insertWeight of our interface
        api.insertUser(
                //Passing the values by getting it from editTexts
                user_name,
                password,
                gender,
                birthYear,
                height,
                weight,
                bmhMul,

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

                        //Displaying the output as a snackbar
                        //Snackbar.make(view, (String)output.toString(), Snackbar.LENGTH_LONG)
                            //    .setAction("Action", null).show();

                        if(Integer.parseInt(output.toString().trim())>0){
                            SharedPreferences preferences = getSharedPreferences("userInfos", 0);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putInt("user_id", Integer.parseInt(output.toString().trim()));
                            editor.putBoolean("first_time", true);
                            editor.commit();

                            successfullLogin();
                        }
                        else{
                            if(Integer.parseInt(output.toString().trim())==-1){
                                Snackbar.make(view, "Sifreniz en az 6 haneli  en çok 12 haneli olmali", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }

                            else if(Integer.parseInt(output.toString().trim())==-2){
                                Snackbar.make(view, "Kullanici adinizi en az 4 haneli en çok 12 haneli olmalı", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else if(Integer.parseInt(output.toString().trim())==-3){
                                Snackbar.make(view, "Dogum yiliniz 1940 ile 2015 arasinda olmali", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else if(Integer.parseInt(output.toString().trim())==-4){
                                    Snackbar.make(view, "Formu gözden geçiriniz. ", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else if(Integer.parseInt(output.toString().trim())==-5){
                                Snackbar.make(view, "Kullanıcı adı mevcut.  ", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else {
                                Snackbar.make(view, "Beklenmedik Hata", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        System.out.println((String)error.toString());
                        Snackbar.make(view, "Baglanılamadı", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );
    }

}

