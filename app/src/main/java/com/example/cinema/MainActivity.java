package com.example.cinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cinema.Fragment.FavoriteFragment;
import com.example.cinema.Fragment.MovieFragment;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieInit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Movie> movieList;
    // Favorite Movie List
    public static ArrayList<Movie> favMovieList;
    public static boolean FRAG_HOME_VISIBILITY = true;
    public static boolean FRAG_FAVORITE_VISIBILITY = false;
    public static boolean FRAG_SEARCH_VISIBILITY = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create MovieInit object to generate Movies
        MovieInit movieInit = new MovieInit(this);
        movieList = movieInit.movieInit();

        // Dummy data for favorite Movie List
        initFavMovieList();

        //Create Bottom Navigation View and set OnClickListener
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
        if(item.getItemId() == R.id.nav_home){
            if (!FRAG_HOME_VISIBILITY) {
                FRAG_HOME_VISIBILITY = true;
                FRAG_FAVORITE_VISIBILITY = false;
                FRAG_SEARCH_VISIBILITY = false;
                //loadFragment(new HomeFragment());
            }
        }
        if (item.getItemId() == R.id.nav_favorite){
            if (!FRAG_FAVORITE_VISIBILITY) {
                FRAG_HOME_VISIBILITY = false;
                FRAG_FAVORITE_VISIBILITY = true;
                FRAG_SEARCH_VISIBILITY = false;
                loadFragment(new FavoriteFragment());
            }
        }
        if (item.getItemId() == R.id.nav_search){
            if (!FRAG_SEARCH_VISIBILITY) {
                FRAG_HOME_VISIBILITY = false;
                FRAG_FAVORITE_VISIBILITY = false;
                FRAG_SEARCH_VISIBILITY = true;
                //loadFragment(new SearchFragment());
            }
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_display, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //Function to return to previous fragment (or exit fullscreen) when pressing Back
    @Override
    public void onBackPressed() {
        if (MovieFragment.isFullScreen()) {
            MovieFragment.exitFullScreen();
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        else {
            super.onBackPressed();
        }
    }
}