package com.example.moviesearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesearcher.Models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    //Wodgets
    private ImageView imageView;
    private TextView title, description;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.textView);
        description = findViewById(R.id.textView2);

        ratingBar = findViewById(R.id.ratingBar);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {

        if (getIntent().hasExtra("movie")) {
            Movie movie = getIntent().getParcelableExtra("movie");
//            Log.v("tags", "intent " + movie.getTitle());

            title.setText(movie.getTitle());
            description.setText(movie.getOverview());
            ratingBar.setRating(movie.getVote_average()/2);

            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path()).into(imageView);
        }
    }
}