package com.hakber.dietgo;

/**
 * Created by qpelit on 30.11.2016.
 */

        import retrofit.Callback;
        import retrofit.client.Response;
        import retrofit.http.Field;
        import retrofit.http.FormUrlEncoded;
        import retrofit.http.POST;


public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/DietGO/foodListInsert.php")

    public void insertUser(

            @Field("food_id") int food_id,
            @Field("foodName") String foodName,
            @Field("calorie") Float calorie,
            @Field("amount") int amount,
            @Field("meal") int meal,
            @Field("user_id") int user_id,
            @Field("date") String date,

            Callback<Response> callback);
}