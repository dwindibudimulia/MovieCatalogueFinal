package com.example.dwindibudimulia.moviecatalogue.notification;

import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;

public interface ReleaseMovieCallbacks {
    void onSuccess(ArrayList<Movie> movies);

    void onFailure(boolean failure);
}
