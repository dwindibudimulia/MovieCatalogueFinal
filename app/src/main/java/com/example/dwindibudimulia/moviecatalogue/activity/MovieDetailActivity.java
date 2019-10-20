package com.example.dwindibudimulia.moviecatalogue.activity;


import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.db.MovieHelper;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;


    private TextView tvName;
    private TextView tvRating;
    private TextView tvDescription;
    private TextView tvDate;
    private TextView tvGenre;
    private TextView tvLanguage;
    private ImageView imgPhoto;
    private ImageView imgBackDrop;
    private ImageView imgBack;
    private ImageView imgFavorite;
    private MovieHelper movieHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvName = findViewById(R.id.txt_name);
        tvRating = findViewById(R.id.txt_rating);
        tvDescription = findViewById(R.id.txt_description);
        tvDate = findViewById(R.id.txt_date);
        tvGenre = findViewById(R.id.txt_genre);
        TextView tvDescriptSub = findViewById(R.id.txt_description_subtitle);
        tvLanguage = findViewById(R.id.txt_language);
        imgPhoto = findViewById(R.id.img_photo);
        imgBackDrop = findViewById(R.id.imgBackdrop);
        imgBack = findViewById(R.id.imgBack);
        imgFavorite = findViewById(R.id.imgFavorite);


        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        openHelper(movie);
        showData(movie);

        imgBack.setOnClickListener(view -> finish());
        imgFavorite.setOnClickListener(view -> {
            if (movie != null) {
                if (movieFavorite(movie)) {
                    movieHelper.deleteMovie(movie.getId());
                    setResult(RESULT_DELETE);
                    imgFavorite.setImageDrawable(getDrawable(R.drawable.ic_un_favorite));
                    Toast.makeText(getApplicationContext(), getString(R.string.remove), Toast.LENGTH_SHORT).show();
                } else {
                    movieHelper.insertMovie(movie);
                    imgFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite));
                    Toast.makeText(getApplicationContext(), getString(R.string.add), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showData(Movie movie) {
        if (movie != null) {
            tvName.setText(movie.getnameMovie());
            tvDate.setText(movie.getdateMovie());
            tvRating.setText(String.valueOf(movie.getRatingMovie()));
            tvGenre.setText(movie.getgenreMovie());
            tvLanguage.setText(movie.getLanguage());
            if (!movie.getdescriptionMovie().equals("")) {
                tvDescription.setText(movie.getdescriptionMovie());
            } else {
                tvDescription.setText(getString(R.string.empty));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tvDescription.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
            }
            imgPhoto.setVisibility(View.VISIBLE);
            Glide.with(MovieDetailActivity.this)
                    .load(movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(imgPhoto);

            imgBack.setVisibility(View.VISIBLE);
            imgBackDrop.setVisibility(View.VISIBLE);
            imgFavorite.setVisibility(View.VISIBLE);
            Glide.with(MovieDetailActivity.this)
                    .load(movie.getBackDrop())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(imgBackDrop);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(movie.getnameMovie());
            }
        }
    }

    private boolean movieFavorite(Movie movie) {
        ArrayList<Movie> list = movieHelper.getALLMovie();
        for (Movie m : list) {
            if (m.getId() == movie.getId())
                return true;
        }
        return false;
    }

    private void openHelper(Movie movie) {
        if (movie != null) {
            movieHelper = MovieHelper.getInstance(getApplicationContext());
            movieHelper.open();
            if (movieFavorite(movie)) {
                imgFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

