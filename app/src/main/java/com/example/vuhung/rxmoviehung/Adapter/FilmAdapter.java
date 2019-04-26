package com.example.vuhung.rxmoviehung.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuhung.rxmoviehung.Model.Film;
import com.example.vuhung.rxmoviehung.R;
import com.example.vuhung.rxmoviehung.View.Signin.SigninActivity;
import com.example.vuhung.rxmoviehung.View.TrailerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.vuhung.rxmoviehung.View.ListFilm.ListFilmActivity.SAVE_LIKE;
import static com.example.vuhung.rxmoviehung.View.Signin.SigninActivity.isSignIn;


public class FilmAdapter extends ArrayAdapter{
    private ArrayList<Film> items;
    private Context context;

    public FilmAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Film> items) {
        super(context, resource, items);
        this.context=context;
        this.items = items;
    }
    public void addListItemAdapter(ArrayList<Film> films){
        items.addAll(films);
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvEnName;
        TextView tvViName;
        Button btnXemPhim;
        TextView tvLike;
        TextView tvDescription;
        ImageView imgFilm ;
        ImageView imgLike;
        TextView tvViews;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        final Film film   = (Film) getItem(position);
        View v = convertView;
        final SharedPreferences sharedPreferences;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.film_adapter, null);
         viewHolder = new ViewHolder();

            viewHolder.tvEnName = (TextView)v.findViewById(R.id.tv_en_name);
            viewHolder.tvViName = (TextView)v.findViewById(R.id.tv_vi_name);
            viewHolder.tvDescription = (TextView)v.findViewById(R.id.tv_description);
            viewHolder.imgFilm = (ImageView)v.findViewById(R.id.img_film);
            viewHolder.imgLike = (ImageView)v.findViewById(R.id.img_like);
            viewHolder.btnXemPhim = (Button) v.findViewById(R.id.btn_xem_phim);
            viewHolder.tvLike = (TextView) v.findViewById(R.id.tv_like);
            viewHolder.tvViews = (TextView) v.findViewById(R.id.tv_views);
            v.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) v.getTag();
        }
        if (film !=null) {
            sharedPreferences = context.getSharedPreferences(SAVE_LIKE,Context.MODE_PRIVATE);
            Picasso.with(context).load(film.getImage()).into(viewHolder.imgFilm);
            int i=0;
            boolean key = true;
            while(i<film.getTitle().length()-1){
                if (film.getTitle().substring(i,i+1).equals("/")){
                    viewHolder.tvViName.setVisibility(v.VISIBLE);
                    viewHolder.tvEnName.setText(film.getTitle().substring(0,i-1));
                    viewHolder.tvViName.setText(film.getTitle().substring(i+2));
                    i=film.getTitle().length();
                    key = false;
                }else {
                    i++;
                }
            }
            if (key){
                viewHolder.tvViName.setVisibility(v.GONE);
                viewHolder.tvEnName.setText(film.getTitle());
            }



            viewHolder.tvViews.setText("Lượt xem: "+film.getViews());



            if ("cam".equals(sharedPreferences.getString(String.valueOf(film.getId()),""))) {
                viewHolder.imgLike.setImageResource(R.drawable.ic_like_orange2x);
                viewHolder.imgLike.setTag(1);
                viewHolder.tvLike.setText("Thích");
                viewHolder.tvLike.setTextColor(context.getResources().getColor(R.color.orange));
                viewHolder.tvLike.setTag(1);
            }
            else {
                viewHolder.imgLike.setImageResource(R.drawable.ic_like2x);//image trắng
                viewHolder.imgLike.setTag(0);
                viewHolder.tvLike.setText("Thích");
                viewHolder.tvLike.setTextColor(context.getResources().getColor(R.color.white));//màu trắng
                viewHolder.tvLike.setTag(0);
            }


            String text = film.getDescription();
            if (text.length() > 135) {
                text = text.substring(0, 135) + "...";
                viewHolder.tvDescription.setText(Html.fromHtml(text));
            }else {
                viewHolder.tvDescription.setText(film.getDescription());
            }
                //đổi màu icon và chữ thích
            viewHolder.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(viewHolder.tvLike.getTag().toString()) == 0){
                        viewHolder.imgLike.setImageResource(R.drawable.ic_like_orange2x);
                        viewHolder.imgLike.setTag(1);
                        viewHolder.tvLike.setTextColor(context.getResources().getColor(R.color.orange));//cam
                        viewHolder.tvLike.setTag(1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(String.valueOf(film.getId()),"cam");
                        editor.commit();

                        }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(String.valueOf(film.getId()),"trang");
                        editor.commit();
                        viewHolder.imgLike.setImageResource(R.drawable.ic_like2x);
                        viewHolder.imgLike.setTag(0);
                        viewHolder.tvLike.setTextColor(context.getResources().getColor(R.color.white));//trang
                        viewHolder.tvLike.setTag(0);

                    }
                }
            });

            viewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(viewHolder.imgLike.getTag().toString()) == 0){
                        viewHolder.imgLike.setImageResource(R.drawable.ic_like_orange2x);
                        viewHolder.imgLike.setTag(1);
                        viewHolder.tvLike.setTextColor(context.getResources().getColor(R.color.orange));//cam
                        viewHolder.tvLike.setTag(1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(String.valueOf(film.getId()),"cam");
                        editor.commit();
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(String.valueOf(film.getId()),"trang");
                        editor.commit();
                        viewHolder.imgLike.setImageResource(R.drawable.ic_like2x);
                        viewHolder.imgLike.setTag(0);
                        viewHolder.tvLike.setTextColor(context.getResources().getColor(R.color.white));//trang
                        viewHolder.tvLike.setTag(0);
                        Log.d("abcd","luu "+ sharedPreferences.getString(String.valueOf(film.getId()),""));
                    }
                }
            });
            viewHolder.btnXemPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("abcd", "film "+ film.getTitle());
                if (isSignIn) {
                    Intent intent = new Intent(context, TrailerActivity.class);
                    intent.putExtra("film", film);
                    context.startActivity(intent);
                   // ((Activity) context).finish();
                }else {
                    Intent intent = new Intent(context, SigninActivity.class);
                    intent.putExtra("film", film);
                    context.startActivity(intent);
                        //  ((Activity) context).finish();
                }
            }
         });
        }
        return v;
    }
}

