package com.example.vuhung.rxmoviehung.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuhung.rxmoviehung.Model.Film;
import com.example.vuhung.rxmoviehung.R;
import com.squareup.picasso.Picasso;

import static com.example.vuhung.rxmoviehung.View.ListFilm.ListFilmActivity.SAVE_LIKE;

public class TrailerActivity extends AppCompatActivity {

    TextView tvTitle2,tvTitle, tvViews, tvGenres, tvActor, tvDirector, tvManufacture, tvDuration, tvLike, tvDescription, tvViewMore;
    ImageView imgLike, imgFilm;
    WebView webView;
    Film film;
    Boolean hideText = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trailer);
        film = getIntent().getExtras().getParcelable("film");
        Log.d("film trailer 2",film.getTitle());
        sharedPreferences = getSharedPreferences(SAVE_LIKE, Context.MODE_PRIVATE);
        init();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);// tắt xoay màn hình
        playFilmTrailer();

        tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTvLike();
            }
        });
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImgLike();
            }
        });
        tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMore();
            }
        });
    }


    /*
    * Function
    * */

    private void viewMore() {
        if (hideText) {
            tvDescription.setMaxLines(100);
            tvDescription.setText(film.getDescription());
            hideText = false;
            tvViewMore.setText(R.string.thu_gon);
        }
        else {
            hideText = true;
            tvDescription.setText(Html.fromHtml(film.getDescription().substring(0,150)+"..."));
            tvViewMore.setText(R.string.xem_them);
        }
    }

    ///play video youtube.
    private void playFilmTrailer() {
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient() {
            // autoplay when finished loading via javascript injection
            public void onPageFinished(WebView view, String url) { webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()"); }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://www.youtube.com/embed/" + getIdUrl(film.getLink())+ "?autoplay=1&vq=small"); //play video youtube with ID
    }

    ////change color icon and text
    private void onClickImgLike() {
        if (Integer.parseInt(imgLike.getTag().toString()) == 0){
            imgLike.setImageResource(R.drawable.ic_like_orange2x);
            imgLike.setTag(1);
            tvLike.setTextColor(getResources().getColor(R.color.orange));//cam
            tvLike.setTag(1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(film.getId()),"cam");
            editor.commit();
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(film.getId()),"trang");
            editor.commit();
            imgLike.setImageResource(R.drawable.ic_like2x);
            imgLike.setTag(0);
            tvLike.setTextColor(getResources().getColor(R.color.white));//trang
            tvLike.setTag(0);
        }
    }

    private void onClickTvLike() {
        if (Integer.parseInt(tvLike.getTag().toString()) == 0){
            imgLike.setImageResource(R.drawable.ic_like_orange2x);
            imgLike.setTag(1);
            tvLike.setTextColor(getResources().getColor(R.color.orange));//cam
            tvLike.setTag(1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(film.getId()),"cam");
            editor.commit();

        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(film.getId()),"trang");
            editor.commit();
            imgLike.setImageResource(R.drawable.ic_like2x);
            imgLike.setTag(0);
            tvLike.setTextColor(getResources().getColor(R.color.white));//trang
            tvLike.setTag(0);
        }
    }

    //lấy id video từ link youtube
    private String getIdUrl(String url){
        String iD="";
        for (int i =0;i<url.length()-2;i++){
            if(url.substring(i,i+2).equals("v=")) {
                iD = url.substring(i+2);
                return iD ;
            }
        }
        return "";
    }

    ///
    private void init() {
        tvTitle2 = findViewById(R.id.tv_title2_film_trailer);
        tvTitle = findViewById(R.id.tv_title_film_trailer);
        tvViews = findViewById(R.id.tv_views_film_trailer);
        tvGenres = findViewById(R.id.tv_genres_film_trailer) ;
        tvActor = findViewById(R.id.tv_actor_film_trailer);
        tvDirector = findViewById(R.id.tv_director_film_trailer);
        tvManufacture = findViewById(R.id.tv_manufacture_film_trailer);
        tvDuration = findViewById(R.id.tv_duration_film_trailer);
        tvLike = findViewById(R.id.tv_like_film_trailer);
        imgLike = findViewById(R.id.img_like_film_trailer);
        tvDescription = findViewById(R.id.tv_description_film_trailer);
        tvViewMore = findViewById(R.id.tv_view_more);
        imgFilm = findViewById(R.id.img_film_trailer);
        webView = findViewById(R.id.web_view);

        int i=0;
        boolean key = true;
        while(i<film.getTitle().length()-1){
            if (film.getTitle().substring(i,i+1).equals("/")){
                tvTitle.setText(film.getTitle().substring(0,i-1));
                tvTitle2.setText(film.getTitle().substring(i+2));
                i=film.getTitle().length();
                key = false;
            }else {
                i++;
            }
        }
        if (key){
            tvTitle.setText("FILM TRAILER");
            tvTitle2.setText(film.getTitle());
        }
        //tvViews.setText("Lượt xem: "+film.getViews());

        tvViews.setText(Html.fromHtml("<html><body>Lượt xem: </font> "+ film.getViews() +"</body><html>"));


        Picasso.with(this).load(film.getImage()).into(imgFilm);;
        tvGenres.setText(Html.fromHtml("<html><body><strong>"+getString(R.string.genres)+ " </strong>" + film.getCategory() +"</body><html>"));
        tvActor.setText(Html.fromHtml("<html><body><strong>"+ getString(R.string.actor)+" </strong>" + film.getActor()+"</body><html>"));
        tvDirector.setText(Html.fromHtml("<html><body><strong>"+ getString(R.string.director)+" </strong>"  + film.getDirector() +"</body><html>"));
        tvManufacture.setText(Html.fromHtml("<html><body><strong>"+ getString(R.string.manufacture) +" </strong>" + film.getManufacturer() +"</body><html>"));
        if ((film.getDuration()+"").equals("null")){
            tvDuration.setText(Html.fromHtml("<html><body><strong>"+ getString(R.string.thoi_luong_phim)+" </strong>" + "không rõ" +"</body><html>"));

        }else {
            tvDuration.setText(Html.fromHtml("<html><body><strong>" + getString(R.string.thoi_luong_phim) + " </strong>" + film.getDuration() + " minute" + "</body><html>"));
        }
        Log.d("duration ", film.getDuration()+"");

        if ("cam".equals(sharedPreferences.getString(String.valueOf(film.getId()),""))) {
            Log.d("filmtrailer ",sharedPreferences.getString(String.valueOf(film.getId()),""));
            imgLike.setImageResource(R.drawable.ic_like_orange2x);
            imgLike.setTag(1);
            tvLike.setText("Thích");
            tvLike.setTextColor(getResources().getColor(R.color.orange));
            tvLike.setTag(1);
        }
        else {
            imgLike.setImageResource(R.drawable.ic_like2x);//image trắng
            imgLike.setTag(0);
            tvLike.setText("Thích");
            tvLike.setTextColor(getResources().getColor(R.color.white));//màu trắng
            tvLike.setTag(0);
        }

        String text = film.getDescription();
        if (text.length() > 150) {
            hideText = true;
            text = text.substring(0, 150) + "...";
            tvDescription.setText(Html.fromHtml(text));
            tvViewMore.setText(R.string.xem_them);
        }else {
            tvViewMore.setVisibility(View.INVISIBLE);
            tvDescription.setText(film.getDescription());
        }
    }

}
