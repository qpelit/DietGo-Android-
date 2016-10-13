package com.hakber.dietgo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class foodList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView customListView = (ListView) findViewById(R.id.listview);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        final String index=getIntent().getExtras().getString("index");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Food> foods = dbHelper.getAllFoods(Integer.parseInt(index));
        MyListAdapter myListAdapter = new MyListAdapter(foodList.this, foods);
        customListView.setAdapter(myListAdapter);
    }

}
