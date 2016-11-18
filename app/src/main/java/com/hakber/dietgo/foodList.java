package com.hakber.dietgo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class foodList extends AppCompatActivity {
    Button btnClosePopup;
    Button btnCreatePopup;

    List<Food> foods;
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

        foods= dbHelper.getAllFoods(Integer.parseInt(index));
        MyListAdapter myListAdapter = new MyListAdapter(foodList.this, foods);
        customListView.setAdapter(myListAdapter);





        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                initiatePopupWindow(position,view);

            }
        });

    }
    private PopupWindow pwindo;

    private void initiatePopupWindow(int pos,View anchorView) {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) foodList.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));




            pwindo = new PopupWindow(foodList.this);
            pwindo.setContentView(layout);
            pwindo.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            pwindo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


            // If the PopupWindow should be focusable
            pwindo.setFocusable(true);

            // If you need the PopupWindow to dismiss when when touched outside
            pwindo.setBackgroundDrawable(new BitmapDrawable());

            int location[] = new int[2];

            // Get the View's(the one that was clicked in the Fragment) location
            anchorView.getLocationOnScreen(location);

            // Using location, the PopupWindow will be displayed right under anchorView
            pwindo.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                    location[0], location[1] + anchorView.getHeight());

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);
            TextView foodTextView = (TextView) findViewById(R.id.foodTextView);
            TextView foodCalorieTextView= (TextView) findViewById(R.id.foodCalorieTextView);
            TextView foodKarbonhidratTextView= (TextView) findViewById(R.id.foodKarbonhidratTextView);
            TextView foodProteinTextView= (TextView) findViewById(R.id.foodProteinTextView);
            TextView foodFatTextView= (TextView) findViewById(R.id.foodFatTextView);
            ((TextView)pwindo.getContentView().findViewById(R.id.foodTextView)).setText(foods.get(pos).getFoodName());
            ((TextView)pwindo.getContentView().findViewById(R.id.foodCalorieTextView)).setText(String.valueOf(foods.get(pos).getCalorie()+" kcal"));
            ((TextView)pwindo.getContentView().findViewById(R.id.foodKarbonhidratTextView)).setText(String.valueOf(foods.get(pos).getCarbo() +" gr"));
            ((TextView)pwindo.getContentView().findViewById(R.id.foodProteinTextView)).setText(String.valueOf(foods.get(pos).getProtein()+" gr"));
            ((TextView)pwindo.getContentView().findViewById(R.id.foodFatTextView)).setText(String.valueOf(foods.get(pos).getFat()+" gr"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };


}
