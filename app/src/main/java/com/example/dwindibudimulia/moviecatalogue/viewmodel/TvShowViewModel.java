package com.example.dwindibudimulia.moviecatalogue.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dwindibudimulia.moviecatalogue.BuildConfig;
import com.example.dwindibudimulia.moviecatalogue.activity.MainActivity;
import com.example.dwindibudimulia.moviecatalogue.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> tvshowList = new MutableLiveData<>();
    private static final String NULL_CONDITION = BuildConfig.IMG_URL_NULL;

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> tvShowListItem = new ArrayList<>();
        String API_KEY = " API_KEY";
        String url = "https://api.themoviedb.org/3/tv/on_the_air?api_key=" + API_KEY + "&language=en-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray tvList = responseObject.getJSONArray("results");
                    for (int i = 0; i < tvList.length(); i++) {
                        JSONObject tvShow = tvList.getJSONObject(i);
                        TvShow itemTvShow = new TvShow(tvShow);
                        if (!itemTvShow.getBackDropTv().equals(NULL_CONDITION) && !itemTvShow.getRatingTv().equals("0.0"))
                            tvShowListItem.add(itemTvShow);
                    }
                    tvshowList.postValue(tvShowListItem);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(MainActivity.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    tvshowList.postValue(tvShowListItem);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", Objects.requireNonNull(error.getMessage()));
                Toast.makeText(MainActivity.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                tvshowList.postValue(tvShowListItem);
            }
        });
    }

    public LiveData<ArrayList<TvShow>> gettvShow() {
        return tvshowList;
    }

    public void setEmptyTv() {
        ArrayList<TvShow> tvShowListItem = new ArrayList<>();
        tvshowList.postValue(tvShowListItem);
    }

    public void setTvShowSearch(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> list = new ArrayList<>();
        String API_KEY = " API_KEY";
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=" + "&query=" + query;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray res = responseObject.getJSONArray("results");
                    for (int i = 0; i < res.length(); i++) {
                        TvShow tvShow = new TvShow(res.getJSONObject(i));
                        if (!tvShow.getBackDropTv().equals(NULL_CONDITION))
                            list.add(tvShow);
                    }
                    tvshowList.postValue(list);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(MainActivity.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    tvshowList.postValue(list);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
                Toast.makeText(MainActivity.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                tvshowList.postValue(list);
            }
        });
    }

}
