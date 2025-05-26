package com.example.upcity.network;

import com.example.upcity.utils.ResponseApplication;
import com.example.upcity.utils.ResponseAuthentication;
import com.example.upcity.utils.ResponseCreateApplication;
import com.example.upcity.utils.RequestRegister;
import com.example.upcity.utils.RequestUtilityCompany;
import com.example.upcity.utils.ResponseGoogleAuthentication;
import com.example.upcity.utils.ResponseUpdateProfile;
import com.example.upcity.utils.ResponseUserSubscription;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface ApiService {
    @GET("/application/all_by_user/")
    Call<List<ResponseApplication>> getUserApplications(@Header("Authorization") String authorization);

    @GET("user/subscription/")
    Call<ResponseUserSubscription> getUserSubscription(@Header("Authorization") String authorization);

    @GET("/application/")
    Call<List<ResponseApplication>> getApplications();

    @GET("/utility_company/")
    Call<List<RequestUtilityCompany>> getUtilityCompany(@Header("Authorization") String authorization);

    @GET("/application/{app_id}/")
    Call<ResponseApplication> getApplicationDetails(
            @Header("Authorization") String token,
            @Path("app_id") int appId
    );

    @POST("/user/")
    Call<ResponseAuthentication> createUser(@Body RequestRegister user);

    @POST("/user/auth/google/")
    Call<ResponseGoogleAuthentication> googleLogin(@Body Map<String, String> body);

    @FormUrlEncoded
    @POST("/login/")
    Call<ResponseAuthentication> login(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("scope") String scope,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @Multipart
    @PUT("user/me/")
    Call<ResponseUpdateProfile> updateUser(
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part("surname") RequestBody surname,
            @Part MultipartBody.Part image,
            @Header("Authorization") String token
    );

    @Multipart
    @POST("/application/create/")
    Call<ResponseCreateApplication> createApplication(
            @Header("Authorization") String authorization,
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("description") RequestBody description,
            @Part("company_name") RequestBody companyName,
            @Part MultipartBody.Part photo
    );

    @POST("/user/process_payment")
    Call<ResponseBody> sendPaymentToken(
            @Header("Authorization") String authHeader,
            @Body Map<String, String> body
    );
}