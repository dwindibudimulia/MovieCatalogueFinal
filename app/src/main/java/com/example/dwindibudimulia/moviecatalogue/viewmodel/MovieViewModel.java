package com.example.dwindibudimulia.moviecatalogue.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dwindibudimulia.moviecatalogue.BuildConfig;
import com.example.dwindibudimulia.moviecatalogue.activity.MainActivity;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.makeText;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();
    private static final String NULL_CONDITION = BuildConfig.IMG_URL_NULL;

    public void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> movieListItem = new ArrayList<>();
        String API_KEY = " API_KEY";
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray movList = responseObject.getJSONArray("results");
                    for (int i = 0; i < movList.length(); i++) {
                        JSONObject movie = movList.getJSONObject(i);
                        Movie itemMovie = new Movie(movie);
                        if (!itemMovie.getBackDrop().equals(NULL_CONDITION) && !itemMovie.getRatingMovie().equals("0.0"))
                            movieListItem.add(itemMovie);
                    }
                    movieList.postValue(movieListItem);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                    makeText(MainActivity.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    movieList.postValue(movieListItem);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", Objects.requireNonNull(error.getMessage()));
                makeText(MainActivity.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                movieList.postValue(movieListItem);
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return movieList;
    }

    public void setEmptyMovie() {
        ArrayList<Movie> movieListItem = new ArrayList<>();
        movieList.postValue(movieListItem);
    }


    public void setMovieSearch(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list = new ArrayList<>();
        String API_KEY = " API_KEY";
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=" + "&query=" + query;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray res = responseObject.getJSONArray("results");
                    for (int i = 0; i < res.length(); i++) {
                        Movie movie = new Movie(res.getJSONObject(i));
                        if (!movie.getBackDrop().equals(NULL_CONDITION))
                            list.add(movie);
                    }
                    movieList.postValue(list);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                    makeText(MainActivity.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    movieList.postValue(list);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", Objects.requireNonNull(error.getMessage()));
                makeText(MainActivity.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                movieList.postValue(list);
            }
        });
    }

}
