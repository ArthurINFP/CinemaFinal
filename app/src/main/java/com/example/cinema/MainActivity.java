package com.example.cinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.cinema.Fragment.FavoriteFragment;
import com.example.cinema.Fragment.HomeFragment;
import com.example.cinema.Fragment.MovieFragment;
import com.example.cinema.Fragment.SearchFragment;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    MovieManager movieManager = MovieManager.getInstance();
    ProgressDialog dialog;
    private Fragment homeFragment, favoriteFragment, searchFragment;
    public static FrameLayout fullscreenFrame;
    public static boolean FRAG_HOME_VISIBILITY = true;
    public static boolean FRAG_FAVORITE_VISIBILITY = false;
    public static boolean FRAG_SEARCH_VISIBILITY = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Collecting movies");
        dialog.setMessage("Please wait for a few second");
        dialog.setCancelable(false);
        dialog.show();;
        // Initialize Movies and store them in the MovieManager
        initMovies();
        // Upload the data into the database (one time only)
        //initMovieInFirebase(movieManager.getMovies());
    }


    private void initMovies() {
//        MovieInit movieInit = new MovieInit(this);
//        ArrayList<Movie> initializedMovies = movieInit.movieInit();
//        movieManager.setMovies(initializedMovies);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Movie> temp = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    movie.setComments(new ArrayList<>());
                    temp.add(movie);
                }
                initView(temp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("setData","Something went wrong");
            }
        });
    }
    public void initView(ArrayList<Movie> temp){
        movieManager.setMovies(temp);
        initFragment();
        initFavMovieList();
        //Create Bottom Navigation View and set OnClickListener
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_menu);
        navigation.setOnNavigationItemSelectedListener(this);
        fullscreenFrame = findViewById(R.id.fullscreen_frame);
        dialog.dismiss();
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        favoriteFragment = new FavoriteFragment();
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_display, homeFragment)
                .commit();
    }

    private void initFavMovieList() {
        ArrayList<Movie> movieList = movieManager.getMovies();
        for (int i = 0; i < 5; i++) {
            movieList.get(i).setFavorite(true);
            movieManager.addToFavorites(movieList.get(i));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home){
            if (!FRAG_HOME_VISIBILITY) {
                FRAG_HOME_VISIBILITY = true;
                FRAG_FAVORITE_VISIBILITY = false;
                FRAG_SEARCH_VISIBILITY = false;
                loadFragment(homeFragment);
                return true;

            }
        }
        if (item.getItemId() == R.id.nav_favorite){
            if (!FRAG_FAVORITE_VISIBILITY) {
                FRAG_HOME_VISIBILITY = false;
                FRAG_FAVORITE_VISIBILITY = true;
                FRAG_SEARCH_VISIBILITY = false;
                loadFragment(favoriteFragment);
                return true;
            }
        }
        if (item.getItemId() == R.id.nav_search){
            if (!FRAG_SEARCH_VISIBILITY) {
                FRAG_HOME_VISIBILITY = false;
                FRAG_FAVORITE_VISIBILITY = false;
                FRAG_SEARCH_VISIBILITY = true;
                loadFragment(searchFragment);
                return true;
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
    public void initMovieInFirebase(ArrayList<Movie> movieList){
        if (movieList != null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("movies");
            for (Movie movie: movieList){
                myRef.child(String.valueOf(movie.getId())).setValue(movie)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("initDatabase","Success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("initDatabase","Failed");
                            }
                        });
            }
        }
    }
}
