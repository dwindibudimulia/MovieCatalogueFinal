package com.example.dwindibudimulia.moviecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.dwindibudimulia.moviecatalogue.db.DatabaseContract;

import org.json.JSONObject;

import java.util.Objects;

public class Movie implements Parcelable {

    private int id;

    private String posterPath;

    private String backDrop;

    private String nameMovie;

    private String ratingMovie;

    private String descriptionMovie;

    private String dateMovie;

    private String genreMovie;

    private String language;

    private String type;

    //JSON
    public Movie(JSONObject movie) {
        try {
            this.id = movie.getInt("id");
            this.nameMovie = movie.getString("title");
            this.ratingMovie = String.valueOf(movie.getDouble("vote_average"));
            this.descriptionMovie = movie.getString("overview");
            this.dateMovie = movie.getString("release_date");
            this.genreMovie = movie.getString("genre_ids");
            this.language = movie.getString("original_language");
            String poster = movie.getString("poster_path");
            String backdrop = movie.getString("backdrop_path");
            this.backDrop = "https://image.tmdb.org/t/p/w185/" + backdrop;
            this.posterPath = "https://image.tmdb.org/t/p/w185/" + poster;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error Data", Objects.requireNonNull(e.getMessage()));
        }
    }

    public Movie(JSONObject result, String type) {
        try {
            if (type.equals("movie")) {
                setnameMovie(result.getString("title"));
                setdateMovie(result.getString("release_date"));
            } else {
                setnameMovie(result.getString("name"));
                setdateMovie(result.getString("first_air_date"));
            }
            setgenreMovie(result.getString("genre_ids"));
            setRatingMovie(result.getString("vote_average"));
            setdescriptionMovie(result.getString("overview"));
            setPosterPath(result.getString("poster_path"));
            setLanguage(result.getString("language"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setType(type);
    }


    public Movie() {

    }

    public Movie(int id, String nameMovie, String ratingMovie, String descriptionMovie, String dateMovie, String genreMovie, String language, String posterPath, String backDrop) {
        this.id = id;
        this.nameMovie = nameMovie;
        this.ratingMovie = ratingMovie;
        this.descriptionMovie = descriptionMovie;
        this.dateMovie = dateMovie;
        this.genreMovie = genreMovie;
        this.language = language;
        this.backDrop = backDrop;
        this.posterPath = posterPath;
    }

    public Movie(Cursor cursor) {
        this.id = DatabaseContract.getColumnInt(cursor, DatabaseContract.MovieColumns._ID);
        this.nameMovie = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.NAME_MOVIE);
        this.ratingMovie = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RATING_MOVIE);
        this.descriptionMovie = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.dateMovie = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.genreMovie = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.GENRE_MOVIE);
        this.language = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.LANGUAGE);
        this.backDrop = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
        this.posterPath = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.POSTER_PATH);
    }


    // Getter
    public int getId() {
        return id;
    }

    public String getnameMovie() {
        return nameMovie;
    }

    public String getRatingMovie() {
        return ratingMovie;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public String getgenreMovie() {
        return genreMovie;
    }

    public String getdateMovie() {
        return dateMovie;
    }

    public String getdescriptionMovie() {
        return descriptionMovie;
    }

    public String getLanguage() {
        return language;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRatingMovie(String ratingMovie) {
        this.ratingMovie = ratingMovie;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }

    public void setgenreMovie(String genreMovie) {
        this.genreMovie = genreMovie;
    }

    public void setdateMovie(String dateMovie) {
        this.dateMovie = dateMovie;
    }

    public void setnameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public void setdescriptionMovie(String descriptionMovie) {
        this.descriptionMovie = descriptionMovie;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.backDrop);
        dest.writeString(this.nameMovie);
        dest.writeString(this.ratingMovie);
        dest.writeString(this.descriptionMovie);
        dest.writeString(this.dateMovie);
        dest.writeString(this.genreMovie);
        dest.writeString(this.language);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.backDrop = in.readString();
        this.nameMovie = in.readString();
        this.ratingMovie = in.readString();
        this.descriptionMovie = in.readString();
        this.dateMovie = in.readString();
        this.genreMovie = in.readString();
        this.language = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
