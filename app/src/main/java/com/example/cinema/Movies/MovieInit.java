package com.example.cinema.Movies;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.cinema.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieInit {
    Context context;

    // Categorized lists
    private ArrayList<Movie> horrorMovies;
    private ArrayList<Movie> actionMovies;
    private ArrayList<Movie> otherMovies;

    public MovieInit(Context context) {
        this.context = context;
    }


    public ArrayList<Movie> movieInit() {
        ArrayList<Movie> movieList = new ArrayList<>();

        horrorMovies = new ArrayList<>();
        actionMovies = new ArrayList<>();
        otherMovies = new ArrayList<>();


        // Collect data from resource strings.xml
        String[] title = context.getResources().getStringArray(R.array.movie_title);
        String[] booking = context.getResources().getStringArray(R.array.movie_booking);
        String[] category = context.getResources().getStringArray(R.array.movie_category);
        String[] description = context.getResources().getStringArray(R.array.movie_description);
        int[] duration = context.getResources().getIntArray(R.array.movie_duration);
        String[] trailer = context.getResources().getStringArray(R.array.movie_trailer);
        String[] releaseDate = context.getResources().getStringArray(R.array.movie_release_date);

        // Obtain the float ratings from the XML resource using TypedArray
        TypedArray ratingsTypedArray = context.getResources().obtainTypedArray(R.array.movie_ratings);
        float[] ratingArray = new float[ratingsTypedArray.length()];
        for (int i = 0; i < ratingsTypedArray.length(); i++) {
            ratingArray[i] = ratingsTypedArray.getFloat(i, -1.0f); // -1.0f is a default value if there's a problem
        }
        ratingsTypedArray.recycle(); // Always recycle a TypedArray when done

        Drawable[] thumbnail = {getImage(R.drawable.kumarn),
                                getImage(R.drawable.exocist),
        getImage(R.drawable.drcheon),
        getImage(R.drawable.pastlives),
                getImage(R.drawable.creator),
                getImage(R.drawable.expandables4),
                getImage(R.drawable.nun2),
                getImage(R.drawable.batmanbegin),
        getImage(R.drawable.hungergames),
                getImage(R.drawable.itlivesinside),
                getImage(R.drawable.nmt),
                getImage(R.drawable.thedarkknight),
                getImage(R.drawable.themarvels),
                getImage(R.drawable.dune2),
                getImage(R.drawable.fnaf),
                getImage(R.drawable.cobweb)};

        // Create a movie list through above data
        for (int i = 0; i < title.length; i++) {
            Movie movie = new Movie(i, thumbnail[i], trailer[i], booking[i], title[i], description[i],
                    200, ratingArray[i], new ArrayList<Comment>(), category[i], duration[i], releaseDate[i], false);

            // Add movie to the corresponding list based on its category
            switch (category[i]) {
                case "Horror":
                    horrorMovies.add(movie);
                    break;
                case "Action":
                    actionMovies.add(movie);
                    break;
                default:
                    otherMovies.add(movie);
                    break;
            }
        }

        // Sort each list by rating in descending order
        Comparator<Movie> ratingComparator = (movie1, movie2) -> Float.compare(movie2.getRating(), movie1.getRating());

        Collections.sort(horrorMovies, ratingComparator);
        Collections.sort(actionMovies, ratingComparator);
        Collections.sort(otherMovies, ratingComparator);

        // Combine all movies into one list (if necessary)
        movieList.addAll(horrorMovies);
        movieList.addAll(actionMovies);
        movieList.addAll(otherMovies);
        return movieList;


    }
    // Get thumbnail image through drawable
    public Drawable getImage(int id){
        return AppCompatResources.getDrawable(context,id);
    }


    public ArrayList<Movie> getHorrorMovies() {
        return horrorMovies;
    }

    public ArrayList<Movie> getActionMovies() {
        return actionMovies;
    }

    public ArrayList<Movie> getOtherMovies() {
        return otherMovies;
    }
}
