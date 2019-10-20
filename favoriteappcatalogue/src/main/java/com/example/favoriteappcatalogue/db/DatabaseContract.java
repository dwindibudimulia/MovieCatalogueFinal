package com.example.favoriteappcatalogue.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    private static final String AUTHORITY = "com.example.dwindibudimulia.moviecatalogue";
    private static final String SCHEME = "content";

    private static final String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {
        public static final String NAME_MOVIE = "name_movie";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP = "backdrop";
        public static final String RATING_MOVIE = "rating_movie";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String GENRE_MOVIE = "genre_movie";
        public static final String LANGUAGE = "language";
    }

    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
