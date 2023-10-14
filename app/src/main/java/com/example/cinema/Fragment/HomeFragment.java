package com.example.cinema.Fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieInit;
import com.example.cinema.R;
import com.example.cinema.RecyclerView.MovieAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {
    private RecyclerView horrorRecyclerView, actionRecyclerView, otherRecyclerView;
    private MovieAdapter horrorAdapter, actionAdapter, otherAdapter;
    private ArrayList<Movie> horrorMoviesList, actionMoviesList, otherMoviesList;
    private boolean isDescendingOrder = true; // default sorting order

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        MovieInit movieInit = new MovieInit(getContext());
        ArrayList<Movie> allMovies = movieInit.movieInit(); // This line collects the full movie list, if needed

        horrorMoviesList = movieInit.getHorrorMovies();
        actionMoviesList = movieInit.getActionMovies();
        otherMoviesList = movieInit.getOtherMovies();

        horrorAdapter = setUpRecyclerView(rootView, R.id.recycler_view_horror, horrorMoviesList);
        actionAdapter = setUpRecyclerView(rootView, R.id.recycler_view_action, actionMoviesList);
        otherAdapter = setUpRecyclerView(rootView, R.id.recycler_view_other, otherMoviesList);

        Button sortButton = rootView.findViewById(R.id.sort_button);
        sortButton.setOnClickListener(v -> toggleSortOrder());

        return rootView;
    }

    private MovieAdapter setUpRecyclerView(View rootView, int recyclerViewId, ArrayList<Movie> moviesList) {
        RecyclerView recyclerView = rootView.findViewById(recyclerViewId);
        MovieAdapter adapter = new MovieAdapter(getContext(), moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void toggleSortOrder() {
        isDescendingOrder = !isDescendingOrder; // Toggle the order flag
        sortMoviesList(horrorMoviesList);
        sortMoviesList(actionMoviesList);
        sortMoviesList(otherMoviesList);
        // Notify the adapters about the data change
        horrorAdapter.notifyDataSetChanged();
        actionAdapter.notifyDataSetChanged();
        otherAdapter.notifyDataSetChanged();
    }

    private void sortMoviesList(ArrayList<Movie> moviesList) {
        Collections.sort(moviesList, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return isDescendingOrder ? Float.compare(m2.getRating(), m1.getRating()) : Float.compare(m1.getRating(), m2.getRating());
            }
        });
    }
}
