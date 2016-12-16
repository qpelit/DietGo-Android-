package com.hakber.dietgo;

/**
 * Created by qpelit on 30.11.2016.
 */

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

    public interface RegisterWeightAPI {
        @FormUrlEncoded
        @POST("/DietGO/insertWeight.php")

        public void insertWeight(

                @Field("weight") Float weight,
                @Field("user_id") int user_id,
                @Field("date") String date,

                Callback<Response> callback);
    }
