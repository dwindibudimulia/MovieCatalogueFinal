package com.example.dwindibudimulia.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TABLE_MOVIE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TABLE_TV;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.NAME_MOVIE,
            DatabaseContract.MovieColumns.POSTER_PATH,
            DatabaseContract.MovieColumns.BACKDROP,
            DatabaseContract.MovieColumns.RATING_MOVIE,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.GENRE_MOVIE,
            DatabaseContract.MovieColumns.LANGUAGE
    );

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TV,
            DatabaseContract.TvShowColumns._ID,
            DatabaseContract.TvShowColumns.NAME_TV,
            DatabaseContract.TvShowColumns.POSTER_PATH,
            DatabaseContract.TvShowColumns.BACKDROP,
            DatabaseContract.TvShowColumns.RATING_TV,
            DatabaseContract.TvShowColumns.RELEASE_DATE,
            DatabaseContract.TvShowColumns.OVERVIEW,
            DatabaseContract.TvShowColumns.GENRE_TV,
            DatabaseContract.TvShowColumns.LANGUAGE
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV);
        onCreate(db);
    }
}
