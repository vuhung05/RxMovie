package com.example.vuhung.rxmoviehung.View.ListFilm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vuhung.rxmoviehung.Adapter.FilmAdapter;
import com.example.vuhung.rxmoviehung.Model.Film;
import com.example.vuhung.rxmoviehung.Presenter.ListFilmPresenter;
import com.example.vuhung.rxmoviehung.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ListFilmActivity extends AppCompatActivity implements ListFilmView{
    public static String SAVE_LIKE = "save data";
    boolean isLoadingNext = true;
    ListView lvFilm;
    ArrayList<Film> listFilm = new ArrayList<Film>();
    View footerView, footerEndList;
    FilmAdapter adapter;
    ListFilmPresenter presenter;
    boolean isLoaded = false, isEndList = false;
    ProgressBar loading;
    int page = 1;
    int perPage=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_film);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);// tắt xoay màn hình
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if (isNetworkConnected()) {
            lvFilm = (ListView) findViewById(R.id.lv_film);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            footerView = inflater.inflate(R.layout.footer_load_more, null);
            LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            footerEndList = inflater2.inflate(R.layout.footer_end_list, null);
            presenter = new ListFilmPresenter(this);
            presenter.getFilmData(page, perPage);
            lvFilm.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (lvFilm.getLastVisiblePosition() - lvFilm.getHeaderViewsCount() -
                            lvFilm.getFooterViewsCount()) >= (listFilm.size() - 1) && (!isEndList)) {
                        page++;
                        presenter.getFilmData(page, perPage);
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }else {
            Toast.makeText(this,"Vui long kiem tra ket noi internet va thu lai",Toast.LENGTH_LONG).show();
        }


/////key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.vuhung.rxmovie",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    //check internet connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @Override
    public void showLoading() {
        lvFilm.addFooterView(footerView);
    }

    @Override
    public void hideLoading() {
        lvFilm.removeFooterView(footerView);
    }

    @Override
    public void displayListMovie( ArrayList<Film> films) {

        if (!isLoaded) {
            isLoaded = true;
            listFilm.addAll(films);
            adapter = new FilmAdapter(getApplicationContext(), R.layout.film_adapter, (ArrayList<Film>) listFilm);
            lvFilm.setAdapter(adapter);
        } else {
            listFilm.addAll(films);
            adapter.notifyDataSetChanged();
            Log.d("ahihi", String.valueOf(listFilm.size()));
        }
    }

    @Override
    public void endList() {
        lvFilm.addFooterView(footerEndList);
        isEndList = true;
    }
}
