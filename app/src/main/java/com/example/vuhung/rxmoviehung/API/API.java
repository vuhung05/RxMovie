package com.example.vuhung.rxmoviehung.API;
import com.example.vuhung.rxmoviehung.API.Response.ListFilm.ModelListFilmResponse;
import com.example.vuhung.rxmoviehung.API.Response.ModelChangePasswordResponse;
import com.example.vuhung.rxmoviehung.API.Response.Signin.ModelSigninResponse;
import com.example.vuhung.rxmoviehung.API.Response.Signin.ModelSigninWithFBResponse;
import com.example.vuhung.rxmoviehung.API.Response.Signup.ModelSignupResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface API {

    //get datafilm
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @FormUrlEncoded
    @POST("/movie/list")
    //Call<FilmsResponse> getListFilmAccess(@Query("page") int page, @Query("per_page") int per_page );

    Call<ModelListFilmResponse> getListFilm(@Field("page") int page,
                                            @Field("per_page") int per_page);

    //signin
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @FormUrlEncoded
    @POST("/user/login")
    Call<ModelSigninResponse> onSigninCall(@Field("email") String email,
                                             @Field("password") String password);

    //signup - register
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @FormUrlEncoded
    @POST("/user/registry")
    Call<ModelSignupResponse> onSignupCall(@Field("full_name") String fullName,
                                           @Field("email") String email,
                                           @Field("password") String password,
                                           @Field("gender") String gender,
                                           @Field("birthday") String birthday
    );

    //Change password
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @FormUrlEncoded
    @POST("/user/forgot-password")
    Call<ModelChangePasswordResponse> onChangePasswordCal(@Field("email") String email);

//    //signin with facebook
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @FormUrlEncoded
    @POST("/user/social-login")
    Call<ModelSigninWithFBResponse> onSigninWithFbCall(@Field("email") String email,
                                                       @Field("full_name") String full_name,
                                                       @Field("facebook_id") String facebook_id,
                                                       @Field("gender") String gender,
                                                       @Field("avatar") String avatar,
                                                       @Field("facebook_token") String facebook_token);
}
