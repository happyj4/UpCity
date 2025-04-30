package com.example.upcity.network;

import com.example.upcity.utils.ApiResponse;
import com.example.upcity.utils.ApplicationRequest;
import com.example.upcity.utils.LoginRequest;
import com.example.upcity.utils.UserRequest;
import com.example.upcity.utils.UtilityCompanyRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @GET("/application/")
    Call<List<ApplicationRequest>> getApplications();

    @GET("/utility_company/")
    Call<List<UtilityCompanyRequest>> getUtilityCompany();

    @POST("/user/")
    Call<ApiResponse> createUser(@Body UserRequest user);

    @POST("/login/")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);
}