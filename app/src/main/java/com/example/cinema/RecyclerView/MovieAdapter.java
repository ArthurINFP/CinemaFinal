package com.example.cinema.RecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.Movies.Movie;
import com.example.cinema.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;
    private ArrayList<Movie> interestList = new ArrayList<>();

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());
        holder.category.setText(movie.getCategory());
        holder.duration.setText(String.valueOf(movie.getDuration()));
        holder.thumbnail.setImageDrawable(movie.getThumbnail());
        holder.rating.setText(String.valueOf(movie.getRating()));
        holder.releaseDate.setText(String.valueOf(movie.getReleaseDate()));

        holder.addToInterest.setOnClickListener(v -> {
            if (!interestList.contains(movie)) {
                interestList.add(movie);
                Toast.makeText(context, "Added to interest list!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.bookNow.setOnClickListener(v -> {
            // Your logic to book a movie
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
