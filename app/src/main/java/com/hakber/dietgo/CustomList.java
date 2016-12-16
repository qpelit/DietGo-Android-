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


public class CustomList extends ArrayAdapter<String> {
    private int food_id;
    private String food_name;
    private float calorie;
    private int amount=0;
    private int meal=0;
    private Activity context;
    JSONArray objectArray;
    public CustomList(Activity context, JSONArray objectArray, ArrayList<String> items) {
        super(context, R.layout.list_view_layout,items);
        this.context = context;
        this.objectArray=objectArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView food_nameText = (TextView) listViewItem.findViewById(R.id.food_nameText);
        TextView calorieText = (TextView) listViewItem.findViewById(R.id.calorieText);
        TextView mealText = (TextView) listViewItem.findViewById(R.id.mealText);
        JSONObject collegeData;
        try {
            collegeData = (JSONObject) objectArray.get(position);
            food_nameText.setText(collegeData.getString(Config.KEY_FOOD_NAME));
            calorieText.setText(String.format("%.2f kcal", ((float) collegeData.getDouble(Config.KEY_CALORIE)*collegeData.getInt(Config.KEY_AMOUNT))));



            switch (collegeData.getInt(Config.KEY_MEAL)) {
                case 0 :
                    mealText.setText("Kahvaltı");
                    break;

                case 1 :
                    mealText.setText("Öğle Yemeği");
                    break;

                case 2 :
                    mealText.setText("Akşam Yemeği");
                    break;

                default :
                    mealText.setText("Atıştırma");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return listViewItem;
    }
}