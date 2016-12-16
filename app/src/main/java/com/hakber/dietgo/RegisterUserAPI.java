package com.hakber.dietgo;

/**
 * Created by qpelit on 13/12/16.
 */
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterUserAPI {
    @FormUrlEncoded
    @POST("/DietGO/insertUser.php")

    public void insertUser(

            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("birthYear") int birthYear,
            @Field("height") int height,
            @Field("weight") float weight,
            @Field("bmhMultiply") float bmhMultiply,



            Callback<Response> callback);
}
