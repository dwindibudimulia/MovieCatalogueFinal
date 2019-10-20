package com.example.dwindibudimulia.moviecatalogue.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dwindibudimulia.moviecatalogue.LoadTvCallback;
import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.adapter.TvShowAdapter;
import com.example.dwindibudimulia.moviecatalogue.db.TvShowHelper;
import com.example.dwindibudimulia.moviecatalogue.model.TvShow;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.dwindibudimulia.moviecatalogue.activity.TvShowDetailActivity.REQUEST_UPDATE;

public class ListTvShowFavoriteActivity extends AppCompatActivity implements LoadTvCallback {
    private RecyclerView recyclerView;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private TvShowAdapter adapter;
    private TvShowHelper tvShowHelper;
    private Context context;

    public Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tv_favorite);
        recyclerView = findViewById(R.id.rv_tvShow_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Favorite Tv Show");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow);

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        adapter = new TvShowAdapter();
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTvAsync(tvShowHelper, this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setTvShow(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getTvShows());
    }

    @Override
    public void preExecute() {
        runOnUiThread(() -> {
        });
    }

    @Override
    public void postExecute(ArrayList<TvShow> tvShow) {
        adapter.setTvShow(tvShow);
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {
        private final WeakReference<TvShowHelper> weakReference;
        private final WeakReference<LoadTvCallback> weakCallback;

        private LoadTvAsync(TvShowHelper tvShowHelper, LoadTvCallback callback) {
            weakReference = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return weakReference.get().getALLTv();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute(tvShows);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_UPDATE) {
                if (resultCode == TvShowDetailActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(TvShowDetailActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                    showSnackBarMessage(getString(R.string.remove));
                }
            }
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
