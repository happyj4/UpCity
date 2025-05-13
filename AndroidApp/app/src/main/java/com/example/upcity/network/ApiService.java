package com.example.upcity.network;

import com.example.upcity.utils.ApiLoginResponse;
import com.example.upcity.utils.ApiResponse;
import com.example.upcity.utils.ApplicationDetailsResponse;
import com.example.upcity.utils.ApplicationRequest;
import com.example.upcity.utils.LoginRequest;
import com.example.upcity.utils.UserRequest;
import com.example.upcity.utils.UtilityCompanyRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
    @GET("/application/all_by_user/")
    Call<List<ApplicationRequest>> getUserApplications(@Header("Authorization") String authorization);

    @GET("/application/")
    Call<List<ApplicationRequest>> getApplications();

    @GET("/utility_company/")
    Call<List<UtilityCompanyRequest>> getUtilityCompany(@Header("Authorization") String authorization);

    @POST("/user/")
    Call<ApiResponse> createUser(@Body UserRequest user);

    @FormUrlEncoded
    @POST("/login/")
    Call<ApiLoginResponse> login(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("scope") String scope,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @GET("/application/{app_id}/")
    Call<ApplicationDetailsResponse> getApplicationDetails(
            @Header("Authorization") String token,
            @Path("app_id") int appId
    );

    @Multipart
    @POST("/application/create/")
    Call<ApiResponse> createApplication(
            @Header("Authorization") String authorization,  // Заголовок для токена
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("description") RequestBody description,
            @Part("company_name") RequestBody companyName,
            @Part MultipartBody.Part photo
    );

}