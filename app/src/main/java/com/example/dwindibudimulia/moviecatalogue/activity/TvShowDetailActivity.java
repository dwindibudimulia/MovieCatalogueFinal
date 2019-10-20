package com.example.dwindibudimulia.moviecatalogue.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.db.TvShowHelper;
import com.example.dwindibudimulia.moviecatalogue.model.TvShow;

import java.util.ArrayList;


public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;


    private TextView txtNameTv;
    private TextView txtRatingTv;
    private TextView txtDescriptionTv;
    private TextView txtDateTv;
    private TextView txtGenreTv;
    private TextView txtLanguage;
    private ImageView imgPhotoTv;
    private ImageView imgBackDropTv;
    private ImageView imgBackTv;
    private ImageView imgFavoriteTv;
    private TvShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        txtNameTv = findViewById(R.id.txt_name_tv);
        txtRatingTv = findViewById(R.id.txt_rating_tv);
        txtDescriptionTv = findViewById(R.id.txt_description_tv);
        txtDateTv = findViewById(R.id.txt_date_tv);
        txtGenreTv = findViewById(R.id.txt_genre_tv);
        TextView txtDescripSub = findViewById(R.id.txt_description_subtitle);
        txtLanguage = findViewById(R.id.txt_language);
        imgPhotoTv = findViewById(R.id.img_photo_tv);
        imgBackDropTv = findViewById(R.id.imgBackdropTv);
        imgBackTv = findViewById(R.id.imgBackTv);
        imgFavoriteTv = findViewById(R.id.imgFavoriteTv);


        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        openHelper(tvShow);
        showData(tvShow);

        imgBackTv.setOnClickListener(view -> finish());

        imgFavoriteTv.setOnClickListener(view -> {
            if (tvShow != null) {
                if (tvShowFavorite(tvShow)) {
                    tvShowHelper.deleteTv(tvShow.getId());
                    setResult(RESULT_DELETE);
                    imgFavoriteTv.setImageDrawable(getDrawable(R.drawable.ic_un_favorite));
                    Toast.makeText(getApplicationContext(), getString(R.string.remove), Toast.LENGTH_SHORT).show();
                } else {
                    tvShowHelper.insertTv(tvShow);
                    imgFavoriteTv.setImageDrawable(getDrawable(R.drawable.ic_favorite));
                    Toast.makeText(getApplicationContext(), getString(R.string.add), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showData(TvShow tvShow) {
        if (tvShow != null) {
            txtNameTv.setText(tvShow.getnameTv());
            txtRatingTv.setText(String.valueOf(tvShow.getRatingTv()));
            txtDateTv.setText(tvShow.getdateTv());
            txtGenreTv.setText(tvShow.getgenreTv());
            txtLanguage.setText(tvShow.getLanguage());
            if (!tvShow.getdescriptionTv().equals("")) {
                txtDescriptionTv.setText(tvShow.getdescriptionTv());
            } else {
                txtDescriptionTv.setText(getString(R.string.empty));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtDescriptionTv.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
            }
            imgPhotoTv.setVisibility(View.VISIBLE);
            Glide.with(TvShowDetailActivity.this)
                    .load(tvShow.getPhotoTv())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(imgPhotoTv);

            imgBackTv.setVisibility(View.VISIBLE);
            imgBackDropTv.setVisibility(View.VISIBLE);
            imgFavoriteTv.setVisibility(View.VISIBLE);
            Glide.with(TvShowDetailActivity.this)
                    .load(tvShow.getBackDropTv())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(imgBackDropTv);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(tvShow.getnameTv());
            }
        }
    }

    private boolean tvShowFavorite(TvShow tvShow) {
        ArrayList<TvShow> list = tvShowHelper.getALLTv();
        for (TvShow t : list) {
            if (t.getId() == tvShow.getId())
                return true;
        }
        return false;
    }

    private void openHelper(TvShow tvShow) {
        if (tvShow != null) {
            tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
            tvShowHelper.open();
            if (tvShowFavorite(tvShow)){
                imgFavoriteTv.setImageDrawable(getDrawable(R.drawable.ic_favorite));
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

