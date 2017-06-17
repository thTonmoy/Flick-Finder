package com.tht.movies.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    public int id;
    public double vote_average;
    public String title, language, overview, release_date;
    public double popularity;
    public String poster_path, backdrop_path;

    public Movie(int id, double vote_average, String title, String language, String overview, String release_date, double popularity, String poster_path, String backdrop_path) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.language = language;
        this.overview = overview;
        this.release_date = release_date;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        vote_average = in.readDouble();
        title = in.readString();
        language = in.readString();
        overview = in.readString();
        release_date = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        backdrop_path = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(vote_average);
        dest.writeString(title);
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
    }



   /*
    {
            "vote_count": 1115,
            "id": 297762,
            "video": false,
            "vote_average": 7.1,
            "title": "Wonder Woman",
            "popularity": 122.934472,
            "poster_path": "/gfJGlDaHuWimErCr5Ql0I8x9QSy.jpg",
            "original_language": "en",
            "original_title": "Wonder Woman",
            "genre_ids": [
                28,
                12,
                14,
                878
            ],
            "backdrop_path": "/hA5oCgvgCxj5MEWcLpjXXTwEANF.jpg",
            "adult": false,
            "overview": "An Amazon princess comes to the world of Man to become the greatest of the female superheroes.",
            "release_date": "2017-05-30"
        },
     */
}

