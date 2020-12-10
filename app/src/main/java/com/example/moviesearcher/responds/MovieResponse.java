package com.example.moviesearcher.responds;

import com.example.moviesearcher.Models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Найти 1 видос
public class MovieResponse {

    // 1 finding the movie object
    @SerializedName("results")
    @Expose
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
