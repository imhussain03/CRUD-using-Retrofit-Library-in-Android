package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface myapi {

    //API call to retrieve data from mysql server
    @GET("fetchData.php")
    Call<List<model>> getModels();


    //API call to insert data into mysql server
    @FormUrlEncoded
    @POST("setData.php")
    Call<model> addData(@Field("name") String name , @Field("city") String city);

    //API call to Update/Edit data

    //API call to Delete data
}
