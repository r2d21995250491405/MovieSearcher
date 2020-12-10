package com.example.moviesearcher.adapters;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesearcher.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Widgets

    TextView title, release_date, duration;
    ImageView imageView;
    RatingBar ratingBar;

    // Click Listener
    OnMovieListener onMovieListener;


    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;


        imageView = itemView.findViewById(R.id.image);
        ratingBar = itemView.findViewById(R.id.ratingBar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
