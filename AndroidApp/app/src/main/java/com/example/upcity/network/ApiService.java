package com.example.upcity.network;

import com.example.upcity.utils.ApiResponse;
import com.example.upcity.utils.Application;
import com.example.upcity.utils.LoginRequest;
import com.example.upcity.utils.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @GET("/application/")
    Call<List<Application>> getApplications();

    @POST("/user/")
    Call<ApiResponse> createUser(@Body UserRequest user);

    @POST("/login/")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);
}