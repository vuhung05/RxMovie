package com.example.vuhung.rxmoviehung.API.Response.ListFilm;

import com.example.vuhung.rxmoviehung.Model.Film;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelListFilmResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("paging")
    @Expose
    private Paging paging;
    @SerializedName("data")
    @Expose
    private  ArrayList<Film> data = null;

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

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public  ArrayList<Film> getData() {
        return data;
    }

    public void setData( ArrayList<Film> data) {
        this.data = data;
    }
}
