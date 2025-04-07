package com.example.upcity;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.Map;

public interface ApiService {
    @GET("/hello")
    Call<Map<String, String>> getMessage();
}