package com.example.cinema.Movies;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.cinema.R;

import java.util.ArrayList;

public class MovieInit {
    Context context;

    public MovieInit(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> movieInit() {
        ArrayList<Movie> movieList = new ArrayList<>();
        // Collect data from resource strings.xml
        String[] title = context.getResources().getStringArray(R.array.movie_title);
        String[] booking = context.getResources().getStringArray(R.array.movie_booking);
        String[] category = context.getResources().getStringArray(R.array.movie_category);
        String[] description = context.getResources().getStringArray(R.array.movie_description);
        int[] duration = context.getResources().getIntArray(R.array.movie_duration);
        String[] trailer = context.getResources().getStringArray(R.array.movie_trailer);
        String[] releaseDate = context.getResources().getStringArray(R.array.movie_release_date);

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
        for (int i=0;i<title.length;i++){
            movieList.add(new Movie(i,thumbnail[i],trailer[i],booking[i],title[i],description[i]
                    ,200, 0f,new ArrayList<String>(),category[i],duration[i],releaseDate[i],false));
        }
        return movieList;
    }
    // Get thumbnail image through drawable
    public Drawable getImage(int id){
        return AppCompatResources.getDrawable(context,id);
    }
}
