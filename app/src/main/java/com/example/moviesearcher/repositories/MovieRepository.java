package com.example.moviesearcher.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesearcher.Models.Movie;
import com.example.moviesearcher.request.MovieApiClient;

import java.nio.file.Path;
import java.util.List;

public class MovieRepository {
    // хранилище для фильмов

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String Query;
    private int Page;
    //LiveData
//    private MutableLiveData<List<Movie>> mMovies;

    public static MovieRepository getInstance() {

        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieApiClient.getMovies();
    }

    public LiveData<List<Movie>> getPopular() {
        return movieApiClient.getMoviesPop();
    }

    // 2 -Calling the method in repository
    public void searchMovieApi(String query, int page) {

        Query = query;
        Page = page;
        movieApiClient.searchMoviesApi(query, page);
    }

    public void searchMoviePop(int page) {


        Page = page;
        movieApiClient.searchMoviesPop(page);
    }

    public void searchNextPage() {
        searchMovieApi(Query, Page + 1);
    }


}
