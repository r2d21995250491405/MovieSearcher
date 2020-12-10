package com.example.moviesearcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviesearcher.Models.Movie;
import com.example.moviesearcher.adapters.MovieRecyclerView;
import com.example.moviesearcher.adapters.OnMovieListener;
import com.example.moviesearcher.request.Servicey;
import com.example.moviesearcher.responds.MovieSearchResponse;
import com.example.moviesearcher.utils.Credentials;
import com.example.moviesearcher.utils.MovieApi;
import com.example.moviesearcher.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerView Adapter;


    // Перед запуском приложения нужно добавить NetWork security config

    Button btn;
    // ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SearchView
        SetupSearchView();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#FF7F50"));
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

//        btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GetRetrofitRespons();
//                GetRetrofitResponseToID();
//            }
//        });
        instRecycler();
        // Вызываю смотрителей за изменениями
        ObserveAnyChange();
        ObservePopularMovies();

        movieListViewModel.searchMoviesPop(1);
//        searchMovieApi("fast", 1);


        //Тест
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Результаты 1 страницы
//                searchMovieApi("holmes", 2);
//            }
//        });
    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    for (Movie movie : movies) {
                        // Get data in log
                        Log.v("Tag", "onChanged: " + movie.getTitle());

                        Adapter.setmMovies(movies);
                    }
                }
            }
        });

    }


    // Смотреть изменения данных
    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    for (Movie movie : movies) {
                        // Get data in log
                        Log.v("Tag", "onChanged: " + movie.getTitle());

                        Adapter.setmMovies(movies);
                    }
                }
            }
        });
    }

    // 4 - Calling method in Main
    //private void searchMovieApi(String query, int page) {
    //movieListViewModel.searchMoviesApi(query, page);
    //}

    // Заполняем recycler данными : Live Data can't be passed with constructor
    private void instRecycler() {
        Adapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // чтобы отобразить все страницы,не только 1
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollHorizontally(1)) {
                    //когда доходим до низа(последний фильм) нужно открывать следующую page
                    movieListViewModel.searchNextP();
                }
            }
        });


    }

    @Override
    public void onMovieClick(int position) {
//        Toast.makeText(this, "The Position " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", Adapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    //    //Беру запрос с Retrofit
//    private void GetRetrofitRespons() {
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
//                Credentials.API_KEY, "Jack Reacher", 1);
//
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if (response.code() == 200) {
//                    Log.v("Tag", "the response here " + response.body().toString());
//
//                    List<Movie> movies = new ArrayList<>(response.body().getMovies());
//                    for (Movie m : movies) {
//                        Log.v("Tag", "The rel date " + m.getOverview());
//                    }
//                } else {
//                    try {
//                        Log.v("Tag", "Error " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    // Поиск через ID
//    private void GetRetrofitResponseToID() {
//
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<Movie> responseCall = movieApi.getMovie(550, Credentials.API_KEY);
//
//        responseCall.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                if (response.code() == 200) {
//                    Movie movie = response.body();
//                    Log.v("Tag", " The Response ID " + movie.getTitle());
//                } else {
//                    try {
//                        Log.v("Tag", "Error " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//
//            }
//        });
//
//
//    }
    //Метод берет введенную информацию
    private void SetupSearchView() {

        final SearchView searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMoviesApi(

                        //нужно взять стринговую строку и страницу и отправить их в метод для поиска фильма
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });

    }
}