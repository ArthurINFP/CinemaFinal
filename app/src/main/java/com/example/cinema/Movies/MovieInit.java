package com.example.cinema.Movies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.cinema.MainActivity;
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
        Drawable[] thumbnail = {getImage(R.drawable.kumarn)};

        // Create a movie list through above data
        for (int i=0;i<title.length;i++){
            movieList.add(new Movie(0,thumbnail[0],trailer[0],booking[0],title[0],description[0]
                    ,200,new ArrayList<String>(),category[0],duration[0]));
        }
        return movieList;
    }
    public Drawable getImage(int id){
        return AppCompatResources.getDrawable(context,id);
    }
}
