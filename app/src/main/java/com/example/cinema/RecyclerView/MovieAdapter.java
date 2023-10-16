package com.example.cinema.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.Fragment.MovieFragment;
import com.example.cinema.MainActivity;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieManager;
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

        if (movie.isFavorite()) {
            holder.addToInterest.setTextColor(Color.RED);
        } else {
            holder.addToInterest.setTextColor(Color.WHITE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieFragment movieFragment = MovieFragment.newInstance(movieList.get(holder.getAdapterPosition() ));
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_display, movieFragment).addToBackStack(null).commit();
            }
        });
        holder.addToInterest.setOnClickListener(v -> {
            if (!MovieManager.getInstance().getFavoriteMovies().contains(movie)) {
                movie.setFavorite(true);
                MovieManager.getInstance().addToFavorites(movie);
                Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show();
                holder.addToInterest.setTextColor(Color.RED);

            } else {
                movie.setFavorite(false);
                MovieManager.getInstance().removeFavoriteMovie(movie);
                Toast.makeText(context, "Removed from favorites!", Toast.LENGTH_SHORT).show();
                holder.addToInterest.setTextColor(Color.WHITE);
            }
            // Notify the adapter about the change
            notifyDataSetChanged();
        });

        holder.bookNow.setOnClickListener(v -> {
            Intent booking = new Intent(Intent.ACTION_VIEW);
            booking.setData(Uri.parse(movie.getBookingUrl()));
            context.startActivity(booking);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
