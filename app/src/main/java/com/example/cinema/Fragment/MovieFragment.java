package com.example.cinema.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinema.MainActivity;
import com.example.cinema.Movies.Comment;
import com.example.cinema.Movies.Movie;
import com.example.cinema.Movies.MovieManager;
import com.example.cinema.R;
import com.example.cinema.RecyclerView.CommentAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class MovieFragment extends Fragment {
    public static final String TOAST_ADDED_TO_FAVORITE = "Added Movie to Favorite list.";
    public static final String TOAST_REMOVED_FROM_FAVORITE = "Removed Movie from Favorite list.";
    public static final String TOAST_NAME_REQUIRED = "Please fill out your name.";
    public static final String TOAST_COMMENT_REQUIRED = "Please fill out your comment.";
    public static final String TOAST_COMMENT_SUBMITTED = "Comment submitted.";
    public static final String TEXT_ADD_FAV = "Add to Favorite";
    public static final String TEXT_REM_FAV = "Remove from Favorite";
    private static final String ARG_MOVIE = "movie";
    private static boolean isFullScreen = false;
    private static YouTubePlayer player;
    private static YouTubePlayerView playerView;

    private Movie movie;
    private CommentAdapter adapter;
    private ImageView favorite;
    private EditText commenter;
    private EditText commentInput;
    private Toast toastInstance;

    private MovieManager movieManager = MovieManager.getInstance();


    public MovieFragment() { }

    public static MovieFragment newInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie)getArguments().getSerializable(ARG_MOVIE);
        }
        adapter = new CommentAdapter(getContext(), movie.getComments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region YouTube Player setup
        playerView = view.findViewById(R.id.Trailer);
        playerView.setEnableAutomaticInitialization(false);
        getLifecycle().addObserver(playerView);

        playerView.addFullscreenListener(new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                isFullScreen = true;
                playerView.setVisibility(View.GONE);
                MainActivity.fullscreenFrame.setVisibility(View.VISIBLE);
                MainActivity.fullscreenFrame.addView(view);
            }

            @Override
            public void onExitFullscreen() {
                isFullScreen = false;
                playerView.setVisibility(View.VISIBLE);
                MainActivity.fullscreenFrame.removeAllViews();
                MainActivity.fullscreenFrame.setVisibility(View.GONE);
            }
        });

        IFramePlayerOptions playerOptions = new IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(1)
                .build();
        playerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                player = youTubePlayer;
                String url = movie.getTrailerUrl();
                if (url.contains("&")) {
                    url = url.substring(0, url.indexOf('&'));
                }
                String videoId = url.substring(url.indexOf("watch?v=")+8);
                player.loadVideo(videoId, 0);
            }
        }, playerOptions);
        //endregion

        //region Set Movie data to View
        TextView title = view.findViewById(R.id.Title);
        title.setText(movie.getTitle());

        favorite = view.findViewById(R.id.Favorite);
        setFavoriteIcon(movie.isFavorite());

        RatingBar rating = view.findViewById(R.id.Rating);
        rating.setRating(movie.getRating());

        TextView ratingValue = view.findViewById(R.id.RatingValue);
        ratingValue.setText("" + movie.getRating());

        TextView price = view.findViewById(R.id.Price);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        price.setText(formatter.format(movie.getTicketPrice()) + "đ");

        TextView category = view.findViewById(R.id.Category);
        category.setText("Category: " + movie.getCategory());

        TextView duration = view.findViewById(R.id.Duration);
        duration.setText("Duration: " + durationFormat(movie.getDuration()));

        TextView releaseDate = view.findViewById(R.id.ReleaseDate);
        releaseDate.setText("Release date: " + movie.getReleaseDate());

        TextView description = view.findViewById(R.id.Description);
        description.setText(movie.getDescription());

        commenter = view.findViewById(R.id.Commenter);
        commentInput = view.findViewById(R.id.WriteComments);

        Button commentSubmit = view.findViewById(R.id.SubmitButton);
        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commenter.getText().length() <= 0) {
                    toast(getContext(), TOAST_NAME_REQUIRED);
                    return;
                }
                if (commentInput.getText().length() <= 0) {
                    toast(getContext(), TOAST_COMMENT_REQUIRED);
                    return;
                }
                submitComment(
                        commenter.getText().toString(),
                        commentInput.getText().toString(),
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())
                );
            }
        });

        RecyclerView comments = view.findViewById(R.id.Comments);
        comments.setAdapter(adapter);
        comments.setLayoutManager(new LinearLayoutManager(getContext()));

        Button bookTicket = view.findViewById(R.id.BookTicket);
        Button addFavorite = view.findViewById(R.id.AddFavorite);
        //Already in favorite -> remove button, else add button
        addFavorite.setText(movie.isFavorite() ? TEXT_REM_FAV : TEXT_ADD_FAV);
        //endregion

        //Book ticket button function
        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(movie.getBookingUrl())));
            }
        });

        //Add to Favorite button function
        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movie.isFavorite()) {
                    movie.setFavorite(false);
                    movieManager.removeFavoriteMovie(movie);
                    setFavoriteIcon(false);
                    addFavorite.setText(TEXT_ADD_FAV);
                    toast(getContext(), TOAST_REMOVED_FROM_FAVORITE);
                } else {
                    movie.setFavorite(true);
                    movieManager.addToFavorites(movie);
                    setFavoriteIcon(true);
                    addFavorite.setText(TEXT_REM_FAV);
                    toast(getContext(), TOAST_ADDED_TO_FAVORITE);
                }
            }
        });


    }

    public static boolean isFullScreen() {
        return isFullScreen;
    }

    public static void exitFullScreen() {
        if (isFullScreen) {
            player.toggleFullscreen();
        }
    }

    private void setFavoriteIcon(boolean value) {
        favorite.setImageResource(value ? R.drawable.ic_favorite_true : R.drawable.ic_favorite_false);
        favorite.setTooltipText(value ? "Favorited" : "Not favorited");
    }

    private void submitComment(String name, String content, String dateTime) {
        Comment comment = new Comment(name, content, dateTime);
        movie.addComment(comment);
        adapter.notifyDataSetChanged();
        commenter.setText("");
        commentInput.setText("");
        toast(getContext(), TOAST_COMMENT_SUBMITTED);
    }

    private String durationFormat(int minutes) {
        String res = "";
        int hours = 0;
        if (minutes >= 60) {
            hours = minutes / 60;
            minutes -= hours * 60;
        }
        if (hours > 0) {
            res = hours + " hours " + minutes + " minutes";
        }
        else {
            res = minutes + " minutes";
        }
        return res;
    }

    private void toast(Context context, String message) {
        if (toastInstance != null) {
            toastInstance.cancel();
        }
        toastInstance = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toastInstance.show();
    }
}