package com.example.dwindibudimulia.moviecatalogue;

import com.example.dwindibudimulia.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public interface LoadTvCallback {
    void preExecute();

    void postExecute(ArrayList<TvShow> tvShow);
}
