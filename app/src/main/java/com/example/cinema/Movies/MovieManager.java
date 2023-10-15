package com.example.cinema.Movies;

import java.util.ArrayList;

public class MovieManager {
    private static final MovieManager ourInstance = new MovieManager();

    public static MovieManager getInstance() {
        return ourInstance;
    }

    private MovieManager() {
    }

    public ArrayList<Movie> movieList = new ArrayList<>();
    public ArrayList<Movie> favMovieList = new ArrayList<>();

    public ArrayList<Movie> getMovies() {
        return movieList;
    }
    public void setMovies(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public void addToFavorites(Movie movie) {
        if (movie.isFavorite() && !favMovieList.contains(movie)) {
            favMovieList.add(movie);
        }
    }

    public void removeFavoriteMovie(Movie movie) {
        favMovieList.remove(movie);
    }

    public ArrayList<Movie> getFavoriteMovies() {
        return favMovieList;
    }
}
