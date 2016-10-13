package com.hakber.dietgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Oluşturduğumuz değişkeni butonumuzla ilişkilendiriyoruz.
        TextView gainedCalorie = (TextView) findViewById(R.id.gainedCalorie);

        //Butonumuza tıklama özelliği kazandırıyoruz.
        gainedCalorie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
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
            dbHelper.insertFood(new Food("Kırmızı Biber",2.3f,2.4f,5.2f,6.2f,"sda", 21));
            dbHelper.insertFood(new Food("Limon",2.3f,2.4f,5.2f,6.2f,"sda", 00));
            dbHelper.insertFood(new Food("Karpuz",2.3f,2.4f,5.2f,6.2f,"sda", 01));
            dbHelper.insertFood(new Food("Tuz",2.3f,2.4f,5.2f,6.2f,"sda", 10));
            dbHelper.insertFood(new Food("Tahrana",2.3f,2.4f,5.2f,6.2f,"sda", 10));

            dbHelper.insertFood(new Food("Kavun",2.3f,2.4f,5.2f,6.2f,"sda", 21));



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
}
