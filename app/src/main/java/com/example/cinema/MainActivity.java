package com.example.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieInit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieInit movieInit = new MovieInit(this);
        movieList = movieInit.movieInit();

    }

}