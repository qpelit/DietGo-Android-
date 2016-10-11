package com.hakber.dietgo;

/**
 * Created by qpelit on 04/10/16.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Food> countryList;

    public MyListAdapter(Activity activity, List<Food> countries) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        countryList = countries;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return countryList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
      if (convertView == null)
            vi = inflater.inflate(R.layout.food_list, null); // create layout from

        TextView textView = (TextView) vi.findViewById(R.id.row_textview); // user name

        Food country = countryList.get(position);

        textView.setText(country.getCountryName());
        return vi;
    }

}