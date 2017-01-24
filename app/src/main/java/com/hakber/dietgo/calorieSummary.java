package com.hakber.dietgo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class calorieSummary extends AppCompatActivity {
    private ProgressDialog loading;
    ListView foodList;
    private TextView totalCal;
    Calendar myCalendar;
    TextView selectedDate;
    String sdate;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        user_id = preferences.getInt("user_id", -1);
        totalCal= (TextView) findViewById(R.id.totalCalorie);
        myCalendar= Calendar.getInstance();
        selectedDate= (TextView) findViewById(R.id.selectedDate);
        ((TextView) findViewById(R.id.advisedCalorie)).setText("1600 kcal");
        sdate=String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                .get(Calendar.YEAR));
        selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH)+1) + "/" + myCalendar
                .get(Calendar.YEAR)));
        foodList=(ListView) findViewById(R.id.listView1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(calorieSummary.this, List1.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
    }

    private void getData() {
        //   String id = editTextId.getText().toString().trim();
        //if (id.equals("")) {
        //   Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
        //  return;
        // }
        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+user_id+"&date="+sdate;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(calorieSummary.this,"Data alınamadı",Toast.LENGTH_LONG).show();
                       // System.out.println(error.getMessage().toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        int food_id = 0;
        String foodName="";
        Float calorie=0f;
        int amount=0;
        int meal=0;
        float totalCalorie=0f;
        String space="      ";
        JSONArray result;
        ArrayList<String> items = new ArrayList<String>();
        JSONObject collegeData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            for(int i=0; i < result.length() ; i++) {

                collegeData = (JSONObject) result.get(i);



                food_id = collegeData.getInt(Config.KEY_FOOD_ID);
                foodName = collegeData.getString(Config.KEY_FOOD_NAME);
                calorie = (float) collegeData.getDouble(Config.KEY_CALORIE);
                amount = collegeData.getInt(Config.KEY_AMOUNT);
                meal = collegeData.getInt(Config.KEY_MEAL);
                items.add(foodName+space+String.valueOf(calorie*amount)+" kcal");
                totalCalorie+=calorie*amount;

            }
            totalCal.setText(String.format("%.2f", (totalCalorie))+" kcal");
            CustomList cl = new CustomList(this, result,items);
            foodList.setAdapter(cl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            sdate=String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                    .get(Calendar.YEAR));
            selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH)+1) + "/" + myCalendar
                    .get(Calendar.YEAR)));
            getData(); // refresh the dates according to new selected date

        }

    };
    public void showTimePickerDialog(View v) {
        new DatePickerDialog(calorieSummary.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}

