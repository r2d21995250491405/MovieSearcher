package com.example.moviesearcher.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesearcher.Models.Movie;
import com.example.moviesearcher.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //Класс для VIEWMODEL

    // говорим app,что данные лежат в хранилище
    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieRepository.getMovies();
    }

    public LiveData<List<Movie>> getPop() {
        return movieRepository.getPopular();
    }

    // 3 - Calling the method in view-model
    public void searchMoviesApi(String query, int page) {
        movieRepository.searchMovieApi(query, page);
    }

    public void searchMoviesPop(int page) {
        movieRepository.searchMoviePop(page);
    }

    public void searchNextP() {
        movieRepository.searchNextPage();
    }

}
