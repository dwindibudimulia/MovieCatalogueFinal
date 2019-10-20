package com.example.dwindibudimulia.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.GENRE_MOVIE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.LANGUAGE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.NAME_MOVIE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.RATING_MOVIE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public ArrayList<Movie> getALLMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + ";", null);
        Movie movie;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setnameMovie(cursor.getString(cursor.getColumnIndexOrThrow(NAME_MOVIE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setBackDrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                movie.setRatingMovie(cursor.getString(cursor.getColumnIndexOrThrow(RATING_MOVIE)));
                movie.setdateMovie(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setdescriptionMovie(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setgenreMovie(cursor.getString(cursor.getColumnIndexOrThrow(GENRE_MOVIE)));
                movie.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(NAME_MOVIE, movie.getnameMovie());
        args.put(POSTER_PATH, movie.getPosterPath());
        args.put(BACKDROP, movie.getBackDrop());
        args.put(RATING_MOVIE, movie.getRatingMovie());
        args.put(RELEASE_DATE, movie.getdateMovie());
        args.put(OVERVIEW, movie.getdescriptionMovie());
        args.put(GENRE_MOVIE, movie.getgenreMovie());
        args.put(LANGUAGE, movie.getLanguage());
        return database.insert(DATABASE_TABLE, null, args);

    }

    public int deleteMovie(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
