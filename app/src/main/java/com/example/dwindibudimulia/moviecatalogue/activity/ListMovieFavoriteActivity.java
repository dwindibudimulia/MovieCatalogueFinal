package com.example.dwindibudimulia.moviecatalogue.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dwindibudimulia.moviecatalogue.LoadMovieCallback;
import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.adapter.MovieAdapter;
import com.example.dwindibudimulia.moviecatalogue.db.MovieHelper;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.dwindibudimulia.moviecatalogue.activity.MovieDetailActivity.REQUEST_UPDATE;

public class ListMovieFavoriteActivity extends AppCompatActivity implements LoadMovieCallback {
    private RecyclerView recyclerView;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieAdapter adapter;
    private MovieHelper movieHelper;

    public Context getContext() {
        return context;
    }

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorite_movie);
        recyclerView = findViewById(R.id.rv_movie_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Favorite Movie");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadMovieAsync(movieHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setMovies(list);

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getMovies());
    }

    @Override
    public void preExecute() {
        runOnUiThread(() -> {
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movie) {

        adapter.setMovies(movie);
    }


    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<MovieHelper> weakReference;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(MovieHelper movieHelper, LoadMovieCallback callback) {
            weakReference = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakReference.get().getALLMovie();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == REQUEST_UPDATE) {
                if (resultCode == MovieDetailActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(MovieDetailActivity.EXTRA_POSITION, 0);
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
