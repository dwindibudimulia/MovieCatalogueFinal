package com.example.dwindibudimulia.moviecatalogue.helper;

import android.database.Cursor;

import com.example.dwindibudimulia.moviecatalogue.model.Movie;
import com.example.dwindibudimulia.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToMovieArrayList(Cursor cursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            moviesList.add(new Movie(cursor));
        }
        return moviesList;
    }

    public static ArrayList<TvShow> mapCursorToTvShowArrayList(Cursor cursor) {
        ArrayList<TvShow> moviesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            moviesList.add(new TvShow(cursor));
        }
        return moviesList;
    }
}
