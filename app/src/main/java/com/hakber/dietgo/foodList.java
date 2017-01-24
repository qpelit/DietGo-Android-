package com.hakber.dietgo;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class foodList extends AppCompatActivity {
    Button btnClosePopup;
    Button btnCreatePopup;
    private Spinner spinner1, spinner2;;
    private Button btnSubmit;
    private String  porsionOrGram;
    private int foodpos;
    public TextView selectedDate;
    Calendar myCalendar;
    DBHelper dbHelper;
    int spinner1position;//0 is for kahvaltı 1 is for öğle yemeği 2 is for akşam 3 is for atıştırma
    int spinner2position;
    DBHandler dbHandler;
    ListView customListView;
    List<Food> foods = new ArrayList<Food>();
int user_id;
   String index;
    int indexint;
    String sdate;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        SharedPreferences preferences= getSharedPreferences("userInfos", 0);
        user_id = preferences.getInt("user_id", -1);
        dbHandler=new DBHandler();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        customListView = (ListView) findViewById(R.id.listview);
        //dbHelper = new DBHelper(getApplicationContext());
        index = getIntent().getExtras().getString("index");
        getFoods(Integer.parseInt(index)-1); // to get Foods from server
      // dbHelper.getAllFoodMealList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private PopupWindow pwindo;

    private void initiatePopupWindow(int pos, View anchorView) {
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

            foodpos=pos;
            // If the PopupWindow should be focusable
            pwindo.setFocusable(true);

            // If you need the PopupWindow to dismiss when when touched outside
            pwindo.setBackgroundDrawable(new BitmapDrawable());

            int location[] = new int[2];

            // Get the View's(the one that was clicked in the Fragment) location
            anchorView.getLocationOnScreen(location);

            // Using location, the PopupWindow will be displayed right under anchorView



            pwindo.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                    location[0], location[1] + 50);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);
            TextView foodTextView = (TextView) findViewById(R.id.foodTextView);
            TextView foodCalorieTextView = (TextView) findViewById(R.id.foodCalorieTextView);
            TextView foodKarbonhidratTextView = (TextView) findViewById(R.id.foodKarbonhidratTextView);
            TextView foodProteinTextView = (TextView) findViewById(R.id.foodProteinTextView);
            TextView foodFatTextView = (TextView) findViewById(R.id.foodFatTextView);
            ((TextView) pwindo.getContentView().findViewById(R.id.foodTextView)).setText(foods.get(pos).getFoodName());
            ((TextView) pwindo.getContentView().findViewById(R.id.foodCalorieTextView)).setText(String.valueOf(foods.get(pos).getCalorie() + " kcal"));
            ((TextView) pwindo.getContentView().findViewById(R.id.foodKarbonhidratTextView)).setText(String.valueOf(foods.get(pos).getCarbo() + " gr"));
            ((TextView) pwindo.getContentView().findViewById(R.id.foodProteinTextView)).setText(String.valueOf(foods.get(pos).getProtein() + " gr"));
            ((TextView) pwindo.getContentView().findViewById(R.id.foodFatTextView)).setText(String.valueOf(foods.get(pos).getFat() + " gr"));

            //spinners and button initiate
           spinner1 = (Spinner) pwindo.getContentView().findViewById(R.id.spinner1);
            btnSubmit = (Button) pwindo.getContentView().findViewById(R.id.btnSubmit);
            spinner2 = (Spinner) pwindo.getContentView().findViewById(R.id.spinner2);

            porsionOrGram=String.valueOf(foods.get(pos).getType());

            myCalendar= Calendar.getInstance();
            sdate=String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                    .get(Calendar.YEAR));
            selectedDate= (TextView) pwindo.getContentView().findViewById(R.id.listDate);
            selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH)+1) + "/" + myCalendar
                    .get(Calendar.YEAR)));
            if(!isFinishing()) {
                addItemsOnSpinner2(porsionOrGram);
                addListenerOnSpinnerItemSelection();

                addListenerOnButton();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };


    // add items into spinner dynamically

    public void addListenerOnSpinnerItemSelection() {
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                spinner1position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DBHandler.insertFoodList(foods.get(foodpos).getId(), foods.get(foodpos).getFoodName(),foods.get(foodpos).getCalorie(),spinner2position, spinner1position, user_id, (String) sdate);
                Intent i = new Intent(foodList.this, calorieSummary.class);
                startActivity(i);
            }
        });


    }

   class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ((TextView) pwindo.getContentView().findViewById(R.id.foodCalorieTextView)).setText(String.format("%.2f", foods.get(foodpos).getCalorie()*(pos+1)) + " kcal");
            ((TextView) pwindo.getContentView().findViewById(R.id.foodKarbonhidratTextView)).setText(String.format("%.2f",foods.get(foodpos).getCarbo()*(pos+1)) + " gr");
            ((TextView) pwindo.getContentView().findViewById(R.id.foodProteinTextView)).setText(String.format("%.2f",foods.get(foodpos).getProtein()*(pos+1)) + " gr");
            ((TextView) pwindo.getContentView().findViewById(R.id.foodFatTextView)).setText(String.format("%.2f", foods.get(foodpos).getFat()*(pos+1)) + " gr");
        spinner2position=pos+1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    public void addItemsOnSpinner2(String porg) {

        List<String> list = new ArrayList<String>();
        if(porg.equals("p")) {
            for(int i=1;i<=20;i++)
            list.add(String.valueOf(i)+ " porsiyon");

        }
        else{
            for(int i=100;i<=5000;i+=100)
                list.add(String.valueOf(i)+ " gram");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Toast.makeText(foodList.this, myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH)+1) + "/" + myCalendar
                    .get(Calendar.YEAR), Toast.LENGTH_LONG).show();
            sdate=String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar
                    .get(Calendar.YEAR));

            selectedDate.setText(String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH)+1) + "/" + myCalendar
                    .get(Calendar.YEAR)));


        }

    };
    public void showTimePickerDialog(View v) {
        new DatePickerDialog(foodList.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void getFoods(int cat) {
        String url=" ";
        spinner.setVisibility(View.VISIBLE);
        if(cat!=-1) { // if category is not the user's custom food list
            url = Config.FOOD_DATA_URL + String.valueOf(cat);
        }
        else{
            url = Config.FOOD_DATA_URL + String.valueOf(user_id)+"0"; //  category will be user_id+0

        }
        StringRequest stringRequest = new StringRequest(url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                spinner.setVisibility(View.GONE);
                showJSON(response);
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(calorieSummary.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        JSONArray result;
        JSONObject collegeData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            if(result!=null){
            for(int i=0; i < result.length() ; i++) {

                collegeData = (JSONObject) result.get(i);

                Food food = new Food();
                food.setId(collegeData.getInt("id"));
                food.setFoodName(collegeData.getString("foodName"));
                food.setCalorie((float) collegeData.getDouble("calorie"));
                food.setFat((float) collegeData.getDouble("fat"));
                food.setCarbo((float) collegeData.getDouble("carbo"));
                food.setProtein((float) collegeData.getDouble("protein"));
                food.setType(collegeData.getString("type"));
                food.setCatagorie(collegeData.getInt("category"));
                foods.add(food);

            }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if(foods!=null)
            createList();

        }

    }
    public void createList(){

        //foods = dbHelper.getAllFoods(Integer.parseInt(index));
        MyListAdapter myListAdapter = new MyListAdapter(foodList.this, foods);
        customListView.setAdapter(myListAdapter);


        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                initiatePopupWindow(position, view);

            }
        });

    }
}
