package com.hakber.dietgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class welcome_screen2 extends AppCompatActivity {
    private EditText user_name;
    private EditText password;
    public static final String LOGIN_URL = "http://hakanpelit.pe.hu/DietGO/volleyLogin.php";

    public static final String KEY_USERNAME="user_name";
    public static final String KEY_PASSWORD="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen2);

        user_name= (EditText) findViewById(R.id.user_name);
        password= (EditText) findViewById(R.id.password);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent( welcome_screen2.this,welcomeScreen.class);
                startActivity(i);


            }
        });
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();


            }
        });
    }
    private void userLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String output) {
                        if(Integer.parseInt(output.trim())>0){
                            if(Integer.parseInt(output.toString().trim())>0){
                                SharedPreferences preferences = getSharedPreferences("userInfos", 0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("user_id", Integer.parseInt(output.toString().trim()));
                                editor.commit();
                                successfullLogin();
                            }
                        }
                        else{
                            Toast.makeText(welcome_screen2.this,"Kullanici adi veya sifreniz yanlis. ",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(welcome_screen2.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,user_name.getText().toString().trim());
                map.put(KEY_PASSWORD,password.getText().toString().trim());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void successfullLogin(){
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("isLogged", true);
        editor.commit();
        Intent i = new Intent( welcome_screen2.this,MainActivity.class);
        startActivity(i);

    }

}
