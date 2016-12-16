package com.hakber.dietgo;

/**
 * Created by qpelit on 04/10/16.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import java.io.Serializable;


public class Food implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String foodName;
    private float calorie;
    private float fat;
    private float carbo;
    private float protein;
    private String type; // porsion or gram p/g
    private int catagorie; //for the list example 01
    private LayoutInflater inflater;
    public Food() {
        super();
    }


    public Food(String foodName, float calorie, float fat, float carbo, float protein, String type, int catagorie) {

        this.foodName = foodName;
        this.calorie = calorie;
        this.fat = fat;
        this.carbo = carbo;
        this.protein = protein;
        this.type = type;
        this.catagorie = catagorie;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public void setCarbo(float carbo) {
        this.carbo = carbo;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCatagorie(int catagorie) {
        this.catagorie = catagorie;
    }

    public float getCalorie() {

        return calorie;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbo() {
        return carbo;
    }

    public String getType() {
        return type;
    }

    public float getProtein() {
        return protein;
    }

    public float getCatagorie() {
        return catagorie;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}