package com.postpc.ex7;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyServer
{
    @GET("/users/{usr}/token/")
    Call <TokenResponse> getUsrToken(@Path("usr") String usr);

    @GET("/user/")
    Call <UserResponse> getUsrInfo(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("/user/edit/")
    Call <UserResponse> EditPrettyName(@Header("Authorization") String token,
                                         @Body PrettyNameObj pretty_name);

    @Headers({"Content-Type: application/json"})
    @POST("/user/edit/")
    Call <UserResponse> EditImageURL(@Header("Authorization") String token,
                                       @Body URLObj image_url);
}