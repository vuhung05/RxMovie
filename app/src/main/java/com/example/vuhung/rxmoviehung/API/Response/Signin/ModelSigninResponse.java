package com.example.vuhung.rxmoviehung.API.Response.Signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSigninResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UserSignin user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserSignin getUser() {
        return user;
    }

    public void setUser(UserSignin user) {
        this.user = user;
    }
}
