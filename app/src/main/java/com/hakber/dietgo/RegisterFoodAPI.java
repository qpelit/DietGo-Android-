package com.hakber.dietgo;

/**
 * Created by qpelit on 15.12.2016.
 */

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface RegisterFoodAPI {
    @FormUrlEncoded
    @POST("/DietGO/insertCustomFood.php")

    public void insertUser(
           // foodName 	calorie 	fat 	carbo 	protein 	type 	category
            @Field("foodName") String foodName,
            @Field("calorie") float calorie,
            @Field("fat") float fat,
            @Field("carbo") float carbo,
           @Field("protein") float protein,
            @Field("type")String type,
           @Field("category") int category,

            Callback<Response> callback);
}