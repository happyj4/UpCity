package com.example.upcity.network;

import com.example.upcity.utils.Application;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;
import java.util.Map;

public interface ApiService {
    @GET("application/")
    Call<List<Application>> getApplications();
}