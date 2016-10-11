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
    private static final String DATABASE_NAME   = "turkcellDB";
    // Contacts table name
    private static final String TABLE_COUNTRIES = "countries";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_COUNTRIES + "(id INTEGER PRIMARY KEY,country_name TEXT,country_code TEXT" + ")";
        Log.d("DBHelper", "SQL : " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        onCreate(db);
    }

    public void insertCountry(Food country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("country_name", country.getCountryName());
        values.put("country_code", country.getCountryCode());

        db.insert(TABLE_COUNTRIES, null, values);
        db.close();
    }

    public List<Food> getAllCountries() {
        List<Food> countries = new ArrayList<Food>();
        SQLiteDatabase db = this.getWritableDatabase();

        // String sqlQuery = "SELECT  * FROM " + TABLE_COUNTRIES;
        // Cursor cursor = db.rawQuery(sqlQuery, null);

        Cursor cursor = db.query(TABLE_COUNTRIES, new String[]{"id", "country_name", "country_code"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Food country = new Food();
            country.setId(cursor.getInt(0));
            country.setCountryName(cursor.getString(1));
            country.setCountryCode(cursor.getString(2));
            countries.add(country);
        }

        return countries;
    }
}