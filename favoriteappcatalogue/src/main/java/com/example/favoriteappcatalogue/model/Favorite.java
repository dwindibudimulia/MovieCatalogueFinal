package com.example.favoriteappcatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.favoriteappcatalogue.db.DatabaseContract;

public class Favorite implements Parcelable {
    private int id;

    private String posterPath;

    private String backDrop;

    private String name;

    private String rating;

    private String description;
//this is my change
    //typo releasedate
    //now releaseDate
    private String releaseDate;

    private String genre;

    private String language;

    /* Getter */
    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getReleasedate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    /* Setter */

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleasedate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLanguage(String language) {
        this.language = language;
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
        dest.writeString(this.name);
        dest.writeString(this.rating);
        dest.writeString(this.description);
        dest.writeString(this.releasedate);
        dest.writeString(this.genre);
        dest.writeString(this.language);
    }

    public Favorite() {
    }

    private Favorite(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.backDrop = in.readString();
        this.name = in.readString();
        this.rating = in.readString();
        this.description = in.readString();
        this.releasedate = in.readString();
        this.genre = in.readString();
        this.language = in.readString();
    }

    public Favorite(Cursor cursor) {
        this.id = DatabaseContract.getColumnInt(cursor, DatabaseContract.MovieColumns._ID);
        this.posterPath = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.POSTER_PATH);
        this.backDrop = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
        this.name = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.NAME_MOVIE);
        this.rating = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RATING_MOVIE);
        this.description = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.releasedate = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.genre = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.GENRE_MOVIE);
        this.language = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.LANGUAGE);

    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
