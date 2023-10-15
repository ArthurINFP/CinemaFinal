package com.example.cinema.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinema.MainActivity;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieInit;
import com.example.cinema.Movies.MovieManager;
import com.example.cinema.R;
import com.example.cinema.RecyclerView.FavoriteAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MOVIE = "movie";


    // TODO: Rename and change types of parameters
    RecyclerView rcv;
    FavoriteAdapter adapter;
    private ArrayList<Movie> data;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    public static FavoriteFragment newInstance(ArrayList<Movie> movie) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        //args.putParcelableArrayList(ARG_MOVIE,movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv);

        ArrayList<Movie> favoriteMovies = MovieManager.getInstance().getFavoriteMovies();

        adapter = new FavoriteAdapter(getActivity(), favoriteMovies);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}