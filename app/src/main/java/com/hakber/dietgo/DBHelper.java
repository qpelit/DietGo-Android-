package com.hakber.dietgo;

        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME   = "dietGoDB";
    // Contacts table name
    private static final String TABLE_FOOD = "foodTable";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_FOOD + "(id INTEGER PRIMARY KEY,foodName TEXT,calorie REAL,fat REAL,carbo REAL,protein REAL,type TEXT,catagorie INTEGER" + ")";
        Log.d("DBHelper", "SQL : " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public void insertFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
     values.put("foodName", food.getFoodName());
     values.put("calorie",food.getCalorie());
     values.put("fat",food.getFat());
     values.put("carbo",food.getCarbo());
     values.put("protein",food.getProtein());
     values.put("type",food.getType());
     values.put("catagorie",food.getCatagorie());



        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    public List<Food> getAllFoods(int i) {
        List<Food> foods = new ArrayList<Food>();
        SQLiteDatabase db = this.getWritableDatabase();

        // String sqlQuery = "SELECT  * FROM " + TABLE_COUNTRIES;
        // Cursor cursor = db.rawQuery(sqlQuery, null);

        Cursor cursor = db.query(TABLE_FOOD, new String[]{"id", "foodName", "calorie","fat","carbo","protein","type","catagorie"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Food food = new Food();
            food.setId(cursor.getInt(0));
            food.setFoodName(cursor.getString(1));
            food.setCalorie(cursor.getFloat(2));
            food.setFat(cursor.getFloat(3));
            food.setCarbo(cursor.getFloat(4));
            food.setProtein(cursor.getFloat(5));
            food.setType(cursor.getString(6));
            food.setCatagorie(cursor.getInt(7));

    if(food.getCatagorie()==i) {
        foods.add(food);
    }
        }

        return foods;
    }
}