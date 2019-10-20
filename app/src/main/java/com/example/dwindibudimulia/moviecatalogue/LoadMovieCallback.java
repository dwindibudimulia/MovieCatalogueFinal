package com.example.dwindibudimulia.moviecatalogue;

import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movie);
}

