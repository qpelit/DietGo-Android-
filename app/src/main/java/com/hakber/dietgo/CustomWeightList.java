package com.hakber.dietgo;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomWeightList extends ArrayAdapter<String> {

    private Activity context;
    JSONArray objectArray;
    public CustomWeightList(Activity context, JSONArray objectArray, ArrayList<String> items) {
        super(context, R.layout.list_view_weight,items);
        this.context = context;
        this.objectArray=objectArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_weight, null, true);
        TextView weightText = (TextView) listViewItem.findViewById(R.id.weightText);
        TextView dateText = (TextView) listViewItem.findViewById(R.id.dateText);
        JSONObject collegeData;
        try {
            collegeData = (JSONObject) objectArray.get(position);
            weightText.setText(String.format("%.2f", ((float) collegeData.getDouble("weight"))));
            dateText.setText(collegeData.getString("date"));



        } catch (JSONException e) {
            e.printStackTrace();
        }



        return listViewItem;
    }
}