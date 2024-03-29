package com.example.dwindibudimulia.moviecatalogue.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;

import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TABLE_MOVIE;

public class WidgetHelper {
    private static final String MOVIE_TABLE = TABLE_MOVIE;

    private static DatabaseHelper databaseHelper;

    private static WidgetHelper INSTANCE;

    private static SQLiteDatabase database;

    private WidgetHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static WidgetHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WidgetHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(MOVIE_TABLE, null, null, null, null, null, null, null);
        Movie movie;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)));
                movie.setnameMovie(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME_MOVIE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH)));
                movie.setBackDrop(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP)));
                movie.setRatingMovie(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RATING_MOVIE)));
                movie.setdateMovie(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE)));
                movie.setdescriptionMovie(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW)));
                movie.setgenreMovie(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.GENRE_MOVIE)));
                movie.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.LANGUAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

}
