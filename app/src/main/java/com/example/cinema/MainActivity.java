package com.example.cinema;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.cinema.Fragment.FavoriteFragment;
import com.example.cinema.Fragment.HomeFragment;
import com.example.cinema.Fragment.MapFragment;
import com.example.cinema.Fragment.MovieFragment;
import com.example.cinema.Fragment.SearchFragment;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    MovieManager movieManager = MovieManager.getInstance();
    ProgressDialog dialog;
    private static int LOGIN_REGISTER_ACTIVITY_REQUEST_CODE= 123;
    private Fragment homeFragment, favoriteFragment, searchFragment, mapFragment;
    public static FrameLayout fullscreenFrame;
    public static boolean FRAG_HOME_VISIBILITY = true;
    public static boolean FRAG_FAVORITE_VISIBILITY = false;
    public static boolean FRAG_SEARCH_VISIBILITY = false;

    public FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivityForResult(new Intent(this, LoginAndRegisterActivity.class),LOGIN_REGISTER_ACTIVITY_REQUEST_CODE);

        // Upload the data into the database (one time only)
        //initMovieInFirebase(movieManager.getMovies());

        //set notification for movies
        createNotificationChannel();
        checkAndRequestNotificationPermission();

        //Map
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REGISTER_ACTIVITY_REQUEST_CODE){
            if (resultCode== Activity.RESULT_OK){
                dialog = new ProgressDialog(this);
                dialog.setTitle("Collecting movies");
                dialog.setMessage("Please wait for a few seconds");
                dialog.setIcon(getDrawable(R.drawable.img));
                dialog.setCancelable(false);
                dialog.show();;
                // Initialize Movies and store them in the MovieManager
                initMovies();
            }
        }

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
        mapFragment = MapFragment.newInstance();
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

    //create notification
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Movie Channel";
            String description = "Channel for Movie Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("movie_channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //check permission and allow user to add notification
    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
                        .putExtra(Settings.EXTRA_CHANNEL_ID, getApplicationInfo().uid);
                startActivity(intent);
            }
        }
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

    public void getLocation() {
        ((MapFragment) mapFragment).mainActivity = this;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MapFragment.FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    ((MapFragment) mapFragment).currentLocation = location;
                    ((MapFragment) mapFragment).map.getMapAsync((MapFragment) mapFragment);
                }
                else {
                    checkLocationService();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MapFragment.FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
            else {
                Toast.makeText(this, "Location permission is denied. Cannot get your location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkLocationService() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gpsEnabled && !networkEnabled) {
            Toast.makeText(this, "Please turn on Location Service," +
                    " then Refresh to search for nearest cinemas.", Toast.LENGTH_SHORT).show();
        }
    }
}
