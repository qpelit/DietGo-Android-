package com.hakber.dietgo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button btnClosePopup;
    Button btnCreatePopup;
    TextView selectedDate;
    TextView breakFastCalorie;
    TextView breakFastSuggestedCalorie;
    TextView launchCalorie;
    TextView launchSuggestedCalorie;
    TextView dinnerCalorie;
    TextView dinnerSuggestedCalorie;
    TextView snackCalorie;
    TextView snackSuggestedCalorie;
    TextView burnedCalorieText;
    TextView gainedCalorie;
    TextView weightText;
    TextView activityText;
    Calendar myCalendar;
    int user_id;
    int currentYear;
    SharedPreferences preferences;
    Intent welcomeIntent ;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences= getSharedPreferences("userInfos", 0);

        Boolean loginBoolean = preferences.getBoolean("isLogged", false);
        welcomeIntent= new Intent(MainActivity.this, welcomeScreen.class);

        if(!loginBoolean) {
    startActivity(welcomeIntent);
}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         preferences= getSharedPreferences("userInfos", 0);
         user_id = preferences.getInt("user_id", -1);
        Boolean firstTime = preferences.getBoolean("first_time", true);
        if(firstTime){
             getUserInfo(); // get user infos to save by sharedPref for the first time
        }



        myCalendar= Calendar.getInstance();
        selectedDate= (TextView) findViewById(R.id.selectedDate);
        selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH)+1 + "/" + myCalendar
                .get(Calendar.YEAR)));
        currentYear= myCalendar.get(Calendar.YEAR);
         breakFastCalorie=(TextView) findViewById(R.id.breakFastCalorie) ;
         breakFastSuggestedCalorie=(TextView) findViewById(R.id.breakFastSuggestedCalorie) ;
         launchCalorie=(TextView) findViewById(R.id.launchCalorie) ;
         launchSuggestedCalorie=(TextView) findViewById(R.id.launchSuggestedCalorie) ;
         dinnerCalorie=(TextView) findViewById(R.id.dinnerCalorie) ;
         dinnerSuggestedCalorie=(TextView) findViewById(R.id.dinnerSuggestedCalorie) ;
         snackCalorie=(TextView) findViewById(R.id.snackCalorie) ;
         snackSuggestedCalorie=(TextView) findViewById(R.id.snackSuggestedCalorie) ;
         gainedCalorie = (TextView) findViewById(R.id.gainedCalorie);
        activityText = (TextView) findViewById(R.id.activityText);

        gainedCalorie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, calorieSummary.class); //calorieSummary
                startActivity(i);
            }
        });
        weightText=(TextView) findViewById(R.id.weightText);
        weightText.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View v){

                Intent i=new Intent(MainActivity.this,weight.class);
                startActivity(i);
            }




        });
        activityText.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View v){

                Intent i=new Intent(MainActivity.this,Activities.class);
                startActivity(i);
            }




        });
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);

        weightText.setText("Kilo: "+String.valueOf(preferences.getFloat("weight", -1))+" kg");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header=navigationView.getHeaderView(0);

        getData();
        setSuggestedCalories();
        setSideNavBar();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.calendar_settings) {
            new DatePickerDialog(MainActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.log_out_text) {
            SharedPreferences preferences= getSharedPreferences("userInfos", 0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("isLogged", false);
            editor.putBoolean("first_time", true);
            editor.commit();
            startActivity(welcomeIntent);
        }
        else if (id== R.id.healthyRestaurant){
            Intent i = new Intent(MainActivity.this, Restoranbul.class); //calorieSummary
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Toast.makeText(MainActivity.this, myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                    .get(Calendar.YEAR), Toast.LENGTH_LONG).show();


            selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                    .get(Calendar.YEAR)));
            getData();
        }

    };

    private void getData() {


        String url = Config.DATA_URL+user_id+"&date="+selectedDate.getText();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(calorieSummary.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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
        float breakFast_Calorie=0f;
        float launch_Calorie=0f;
        float dinner_Calorie=0f;
        float snack_Calorie=0f;
        float total_Calorie=0f;

        JSONArray result;

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
                total_Calorie+=calorie*amount;
                switch (meal) {
                    case 0 :
                    breakFast_Calorie+=calorie*amount;
                        break;

                    case 1 :
                        launch_Calorie+=calorie*amount;
                        break;

                    case 2 :
                        dinner_Calorie+=calorie*amount;
                        break;

                    default :
                        snack_Calorie+=calorie*amount;

                        break;
                }

                }
            breakFastCalorie.setText(String.format("%.2f", (breakFast_Calorie))+" kcal");
            launchCalorie.setText(String.format("%.2f", (launch_Calorie))+" kcal");
            dinnerCalorie.setText(String.format("%.2f", (dinner_Calorie))+" kcal");
            snackCalorie.setText(String.format("%.2f", (snack_Calorie))+" kcal");
            gainedCalorie.setText(String.format("%.2f", (total_Calorie))+" kcal aldınız");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getUserInfo(){


            String url = Config.USER_DATA_URL+user_id;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // loading.dismiss();
                    showJSONDatas(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                             Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    private void showJSONDatas(String response) {
        String user_name;
        String gender;
        int birthYear;
        int height;
        float weight;
        float bmhMultiply;


        JSONArray result;

        JSONObject collegeData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            collegeData = (JSONObject) result.get(0);
            user_name = collegeData.getString("user_name");
            gender = collegeData.getString("gender");
            height = collegeData.getInt("height");
            birthYear = collegeData.getInt("birthYear");
            weight = (float) collegeData.getDouble("weight");
            bmhMultiply = (float) collegeData.getDouble("bmhMultiply");


            Toast.makeText(this, String.valueOf(user_name) + " " + gender + String.valueOf(height) + "  " + String.valueOf(birthYear) + " " + String.valueOf(weight) + " " + String.valueOf(bmhMultiply), Toast.LENGTH_LONG).show();


            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("user_name", user_name);
            editor.putString("gender", gender);
            editor.putInt("height", height);
            editor.putInt("birthYear", birthYear);
            editor.putFloat("weight", weight);
            editor.putFloat("bmhMultiply", bmhMultiply);
            editor.putBoolean("first_time", false);
            editor.commit();

            setSideNavBar();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
public void setSuggestedCalories() {
    String gender = preferences.getString("gender", " ");
    int height = preferences.getInt("height", 0);
    int birthYear = preferences.getInt("birthYear", 0);
    float weight = preferences.getFloat("weight", 0f);
    float bmhMultiply = preferences.getFloat("bmhMultiply", 0f);

    float suggestedCalorie = 0f;
    if (gender.equals("Erkek")) {

        suggestedCalorie = (float) ((66 + (13.75 * weight) + (5 * height)) - (6.8 * (2016 - birthYear)));
        float x = suggestedCalorie * bmhMultiply;

        breakFastSuggestedCalorie.setText(String.valueOf(x / 3 + x / 64 - x / 36));
        launchSuggestedCalorie.setText(String.valueOf(x / 3 + x / 64 - x / 36));
        dinnerSuggestedCalorie.setText(String.valueOf(x / 3 - x / 32 - x / 36));
        snackSuggestedCalorie.setText(String.valueOf(x / 12));

    } else {
        suggestedCalorie = (float) ((665 + (9.6 * weight) + (1.7 * height)) - (4.7 * (currentYear - birthYear)));
        float x = suggestedCalorie * bmhMultiply;

        breakFastSuggestedCalorie.setText(String.valueOf(x / 3 + x / 64 - x / 36));
        launchSuggestedCalorie.setText(String.valueOf(x / 3 + x / 64 - x / 36));
        dinnerSuggestedCalorie.setText(String.valueOf(x / 3 - x / 32 - x / 36));
        snackSuggestedCalorie.setText(String.valueOf(x / 12));


    }
}
    public void setSideNavBar(){

        String userName = preferences.getString("user_name","unKnown");
        String gender = preferences.getString("gender","unKnown");
        int birthYear = preferences.getInt("birthYear",0);

        TextView userNav= (TextView) header.findViewById(R.id.userNameNavText);
        TextView infoNav= (TextView) header.findViewById(R.id.userGenderAgeInfoNavText);
        userNav.setText(userName);
        infoNav.setText(String.valueOf(currentYear - birthYear) + " ," + gender);

    }




}
