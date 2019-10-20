package com.example.dwindibudimulia.moviecatalogue.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    static final String AUTHORITY = "com.example.dwindibudimulia.moviecatalogue";
    private static final String SCHEME = "content";

    static final String TABLE_MOVIE = "movie";

    static final String TABLE_TV = "tv_show";

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

    public static final class TvShowColumns implements BaseColumns {
        public static final String NAME_TV = "name_tv";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP = "backdrop";
        public static final String RATING_TV = "rating_TV";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String GENRE_TV = "genre_TV";
        public static final String LANGUAGE = "language";
    }

    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_TV)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
