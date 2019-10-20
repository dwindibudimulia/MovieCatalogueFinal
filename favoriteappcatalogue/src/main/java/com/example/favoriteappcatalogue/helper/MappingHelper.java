package com.example.favoriteappcatalogue.helper;

import android.database.Cursor;

import com.example.favoriteappcatalogue.model.Favorite;

import java.util.ArrayList;

public class MappingHelper {
  public static ArrayList<Favorite> mapCursorToMovieArrayList(Cursor cursor){
        ArrayList<Favorite> moviesList = new ArrayList<>();
        while (cursor.moveToNext()){
            moviesList.add(new Favorite(cursor));
        }
        return moviesList;
    }
}
