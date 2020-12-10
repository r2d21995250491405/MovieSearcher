package com.example.moviesearcher.utils;

import com.example.moviesearcher.Models.Movie;
import com.example.moviesearcher.responds.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    // Ищу фильм
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // для популрных фильмов запрос
    //https://api.themoviedb.org/3/movie/popular

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page

    );

    // Поиск по ID
    // https://api.themoviedb.org/3/movie/550?api_key=aa323fabb521caaecf1fc3429b754316
    // 550 для Jack Reacher

    @GET("/3/movie/{movie_id}?")
    Call<Movie> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );


}
