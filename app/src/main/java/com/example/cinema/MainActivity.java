package com.example.cinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cinema.Fragment.FavoriteFragment;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieInit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Movie> movieList;
    public static ArrayList<Movie> favMovieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieInit movieInit = new MovieInit(this);
        movieList = movieInit.movieInit();
        initFavMovieList();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_menu);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void initFavMovieList() {
        favMovieList = new ArrayList<>();
        for (int i=0;i<5;i++){
            movieList.get(i).setFavorite(true);
            favMovieList.add(movieList.get(i));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_favorite){
            loadFragment(new FavoriteFragment());
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_display,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}