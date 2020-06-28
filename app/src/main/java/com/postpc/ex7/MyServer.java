package com.postpc.ex7;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyServer
{
    @GET("/users/{usr}/token")
    Call <TokenResponse> getUsrToken(@Path("usr") String usr);

    @GET("/user")
    Call <UserResponse> getUsrInfo(@Header("Authorization") String token);
}
