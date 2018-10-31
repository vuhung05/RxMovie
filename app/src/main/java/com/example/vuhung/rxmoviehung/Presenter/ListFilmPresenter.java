package com.example.vuhung.rxmoviehung.Presenter;

import android.util.Log;

import com.example.vuhung.rxmoviehung.API.API;
import com.example.vuhung.rxmoviehung.API.APIManager;
import com.example.vuhung.rxmoviehung.API.Response.ListFilm.ModelListFilmResponse;
import com.example.vuhung.rxmoviehung.Model.Film;
import com.example.vuhung.rxmoviehung.View.ListFilm.ListFilmView;

import java.util.ArrayList;


import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListFilmPresenter {
    ListFilmView listFilmView;

    public ListFilmPresenter(ListFilmView view) {
        this.listFilmView = view;
    }
    public void getFilmData(int page, int perPage) {
        this.listFilmView.showLoading();
        API service = APIManager.getClient().create(API.class);
        Call<ModelListFilmResponse> call = service.getListFilm(page,perPage);
        call.enqueue(new Callback<ModelListFilmResponse>() {
            @Override
            public void onResponse(Response<ModelListFilmResponse> response, Retrofit retrofit) {
                int totalPage = response.body().getPaging().getTotalPages();
                int currentPage = response.body().getPaging().getCurrentPage();
                if (currentPage<=totalPage){
                }else {
                    listFilmView.endList();
                }
                ArrayList<Film> records = response.body().getData();
               if (records.size()!=0){
                   Log.d("abcd",records.get(0).getTitle() +"");
                    listFilmView.displayListMovie(records);
               }
                listFilmView.hideLoading();
            }
            @Override
            public void onFailure(Throwable t) {
                listFilmView.hideLoading();
            }
        });
    }
}
