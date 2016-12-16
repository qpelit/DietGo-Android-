package com.hakber.dietgo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class weight extends AppCompatActivity {
        ListView weightList;
    TextView lastWeightValueText;
    TextView currentWeightValueText;
    int user_id;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        user_id = preferences.getInt("user_id", -1);
        weightList=(ListView) findViewById(R.id.weightListView);
        lastWeightValueText=(TextView) findViewById(R.id.lastWeightValueText) ;
        currentWeightValueText=(TextView) findViewById(R.id.currentWeightValueText) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabWeight);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(weight.this, weightAdd.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWeightData(); // to set Weight List by getting weight data from server
    }
    private void getWeightData() {
        //   String id = editTextId.getText().toString().trim();
        //if (id.equals("")) {
        //   Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
        //  return;
        // }
        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        spinner.setVisibility(View.VISIBLE);
        String url = Config.WEIGHT_DATA_URL+user_id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                spinner.setVisibility(View.GONE);
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(weight.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        int id;
        Float weight=0f;
        String date;
        JSONArray result;
        ArrayList<String> items = new ArrayList<String>();
        JSONObject collegeData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            for(int i=0; i < result.length() ; i++) {

                collegeData = (JSONObject) result.get(i);
                id= collegeData.getInt("id");
                weight = (float) collegeData.getDouble("weight");
                date = collegeData.getString("date");
                items.add(String.valueOf(weight));
            }
            if(result.length() - 2>=0) {
                lastWeightValueText.setText(items.get(result.length() - 2) + " kg");
                currentWeightValueText.setText(items.get(result.length() - 1) + " kg");
            }
            else{
                lastWeightValueText.setText(items.get(result.length() - 1) + " kg");
                currentWeightValueText.setText(items.get(result.length() - 1) + " kg");
            }

            CustomWeightList cl = new CustomWeightList(this, result,items);
            weightList.setAdapter(cl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
