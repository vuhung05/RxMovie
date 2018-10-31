package com.example.vuhung.rxmoviehung.View.ListFilm;

import com.example.vuhung.rxmoviehung.Model.Film;

import java.util.ArrayList;
import java.util.List;

public interface ListFilmView {
    void showLoading();
    void hideLoading();
    void showError();
    void displayListMovie( ArrayList<Film> listFilm);
    void endList();
}
