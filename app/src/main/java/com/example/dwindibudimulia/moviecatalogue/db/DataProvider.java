package com.example.dwindibudimulia.moviecatalogue.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV_SHOW = 3;
    private static final int TV_SHOW_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    private TvShowHelper tvShowHelper;

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_MOVIE + "/#", MOVIE_ID);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_TV, TV_SHOW);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_TV + "/#", TV_SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        tvShowHelper = TvShowHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        movieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV_SHOW:
                cursor = tvShowHelper.queryProvider();
                break;
            case TV_SHOW_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
