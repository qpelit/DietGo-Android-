package com.hakber.dietgo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button btnClosePopup;
    Button btnCreatePopup;
    TextView selectedDate;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myCalendar= Calendar.getInstance();
        selectedDate= (TextView) findViewById(R.id.selectedDate);
        selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "." + myCalendar.get(Calendar.MONTH) + "." + myCalendar
                .get(Calendar.YEAR)));

        TextView gainedCalorie = (TextView) findViewById(R.id.gainedCalorie);

        gainedCalorie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, calorieSummary.class);
                startActivity(i);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ListView customListView = (ListView) findViewById(R.id.listview);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SharedPreferences settings = getSharedPreferences("SQLFQw", 0);
        boolean firstTime = settings.getBoolean("firstTime", true);

        if (firstTime) {
            // Food(String foodName, float calorie, float fat, float carbo, float protein, String type, int catagorie)
            dbHelper.insertFood(new Food("Kırmızı Biber",2.3f,2.4f,5.2f,6.2f,"p", 21));
            dbHelper.insertFood(new Food("Limon",2.3f,2.4f,5.2f,6.2f,"g", 00));
            dbHelper.insertFood(new Food("Karpuz",2.3f,2.4f,5.2f,6.2f,"p", 01));
            dbHelper.insertFood(new Food("Tuz",2.3f,2.4f,5.2f,6.2f,"p", 10));
            dbHelper.insertFood(new Food("Tahrana",2.3f,2.4f,5.2f,6.2f,"p", 10));

            dbHelper.insertFood(new Food("Kavun",2.3f,2.4f,5.2f,6.2f,"p", 21));



            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
        }

/*
        List<Food> countries = dbHelper.getAllCountries();
        MyListAdapter myListAdapter = new MyListAdapter(MainActivity.this, countries);
        customListView.setAdapter(myListAdapter);
        */
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

        }

    };


}
