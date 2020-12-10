package com.example.moviesearcher.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesearcher.R;

public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Widgets
    ImageView imageView22;
    RatingBar ratingBar22;

    // Click Listener
    OnMovieListener onMovieListenerPop;



    public PopularViewHolder(@NonNull View itemView, OnMovieListener onMovieListenerPop) {
        super(itemView);

        this.onMovieListenerPop = onMovieListenerPop;

        imageView22 = itemView.findViewById(R.id.image3);
        ratingBar22 = itemView.findViewById(R.id.ratingBar3);
    }

    @Override
    public void onClick(View v) {
        onMovieListenerPop.onMovieClick(getAdapterPosition());
    }
}
