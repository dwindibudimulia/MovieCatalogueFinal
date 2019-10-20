package com.example.dwindibudimulia.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dwindibudimulia.moviecatalogue.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TABLE_TV;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.BACKDROP;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.GENRE_TV;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.LANGUAGE;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.NAME_TV;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.OVERVIEW;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.POSTER_PATH;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.RATING_TV;
import static com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract.TvShowColumns.RELEASE_DATE;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public ArrayList<TvShow> getALLTv() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + ";", null);
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setnameTv(cursor.getString(cursor.getColumnIndexOrThrow(NAME_TV)));
                tvShow.setPhotoTv(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                tvShow.setBackDropTv(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                tvShow.setRatingTv(cursor.getString(cursor.getColumnIndexOrThrow(RATING_TV)));
                tvShow.setdateTv(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                tvShow.setdescriptionTv(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tvShow.setgenreTv(cursor.getString(cursor.getColumnIndexOrThrow(GENRE_TV)));
                tvShow.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShow.getId());
        args.put(NAME_TV, tvShow.getnameTv());
        args.put(POSTER_PATH, tvShow.getPhotoTv());
        args.put(BACKDROP, tvShow.getBackDropTv());
        args.put(RATING_TV, tvShow.getRatingTv());
        args.put(RELEASE_DATE, tvShow.getdateTv());
        args.put(OVERVIEW, tvShow.getdescriptionTv());
        args.put(GENRE_TV, tvShow.getgenreTv());
        args.put(LANGUAGE, tvShow.getLanguage());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id) {
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


