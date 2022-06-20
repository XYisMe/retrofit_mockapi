package com.example.qrcodescanwithretrofitvolleyandmockapi;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;



public interface RetrofitApi {
    //scan
    //retrieve full list
    //Insert new history
    @POST("History") //endpoint to add new history
    Call<History> insertHistory(@Body History history);

    @GET("History") // endpoint to get all history
    Call<List<History>> getHistories();

}
