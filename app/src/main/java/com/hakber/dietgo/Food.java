package com.hakber.dietgo;

/**
 * Created by qpelit on 04/10/16.
 */
import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String foodName;
    private float calorie;
    private float fat;
    private float carbo;
    private float protein;
    private float type; // porsion or gram p/g
    private float catagorie; //for the list example 01



    public Food() {
        super();
    }


}