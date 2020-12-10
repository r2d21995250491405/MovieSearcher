package com.example.moviesearcher.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesearcher.AppExecutors;
import com.example.moviesearcher.Models.Movie;
import com.example.moviesearcher.responds.MovieSearchResponse;
import com.example.moviesearcher.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

// мост между livedata и retrofit
public class MovieApiClient {

    //LiveData for search
    private MutableLiveData<List<Movie>> mMovies;
    private static MovieApiClient instance;

    // Global runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //LiveData for popular movies
    private MutableLiveData<List<Movie>> mMoviesPopular;

    // Global Popular runnable
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePopular;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
    }


    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public LiveData<List<Movie>> getMoviesPop() {
        return mMoviesPopular;
    }

    // 1 - метод,который вызывается через классы
    public void searchMoviesApi(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);

    }

    public void searchMoviesPop(int pageNumber) {

        if (retrieveMoviesRunnablePopular != null) {
            retrieveMoviesRunnablePopular = null;
        }

        retrieveMoviesRunnablePopular = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePopular);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);

    }

    //получаем данные из api с помощью Runnable класс
    // 2 способа для поиска фильма : ID and Query
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int numberPage;
        boolean cancelRequest;


        public RetrieveMoviesRunnable(String query, int numberPage) {
            this.query = query;
            this.numberPage = numberPage;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            // get response body
            try {
                Response response = getMovies(query, numberPage).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    assert response.body() != null;
                    List<Movie> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (numberPage != 0) {
                        // Sending data to LiveData
                        // PostValue: used for background thread
                        // setValue: not for background thread
                        mMovies.postValue(list);
                    } else {
                        List<Movie> currentMovies = mMovies.getValue();
                        assert currentMovies != null;
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        //Search method/ query
        private Call<MovieSearchResponse> getMovies(String query, int numberPage) {
            return Servicey.getMovieApi().searchMovie(Credentials.API_KEY,
                    query, numberPage);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancel");
            cancelRequest = true;

        }

    }

    private class RetrieveMoviesRunnablePop implements Runnable {

        private int numberPage;
        boolean cancelRequest;


        public RetrieveMoviesRunnablePop(int numberPage) {

            this.numberPage = numberPage;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            // get response body
            try {
                Response response2 = getPop(numberPage).execute();
                if (cancelRequest) {
                    return;
                }
                if (response2.code() == 200) {
                    assert response2.body() != null;
                    List<Movie> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (numberPage != 0) {
                        // Sending data to LiveData
                        // PostValue: used for background thread
                        // setValue: not for background thread
                        mMoviesPopular.postValue(list);
                    } else {
                        List<Movie> currentMovies = mMoviesPopular.getValue();
                        assert currentMovies != null;
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }
                } else {
                    assert response2.errorBody() != null;
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }
        }

        //Search method/ query
        private Call<MovieSearchResponse> getPop(int numberPage) {
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    numberPage);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancel");
            cancelRequest = true;

        }

    }


}
