package com.example.cinema.Fragment;

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

import com.example.cinema.Movies.Comment;
import com.example.cinema.Movies.Movie;
import com.example.cinema.R;
import com.example.cinema.RecyclerView.CommentAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class MovieFragment extends Fragment {
    private static final String ARG_MOVIE = "movie";
    private static boolean isFullScreen = false;
    private static YouTubePlayer player;

    private Movie mMovie;
    private CommentAdapter adapter;
    EditText commenter;
    EditText commentInput;

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
            mMovie = (Movie)getArguments().getSerializable(ARG_MOVIE);
        }
        adapter = new CommentAdapter(getContext(), mMovie.getComments());
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
        YouTubePlayerView playerView = view.findViewById(R.id.Trailer);
        playerView.setEnableAutomaticInitialization(false);
        getLifecycle().addObserver(playerView);

        FrameLayout fullScreenFrame = view.findViewById(R.id.fullscreen_frame);

        playerView.addFullscreenListener(new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                isFullScreen = true;
                playerView.setVisibility(View.GONE);
                fullScreenFrame.setVisibility(View.VISIBLE);
                fullScreenFrame.addView(view);
            }

            @Override
            public void onExitFullscreen() {
                isFullScreen = false;
                playerView.setVisibility(View.VISIBLE);
                fullScreenFrame.removeAllViews();
                fullScreenFrame.setVisibility(View.GONE);
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
                String url = mMovie.getTrailerUrl();
                url = url.substring(0, url.indexOf('&'));
                String videoId = url.substring(url.indexOf("watch?v=")+8);
                player.loadVideo(videoId, 0);
            }
        }, playerOptions);
        //endregion

        //region Set Movie data to View
        TextView title = view.findViewById(R.id.Title);
        title.setText(mMovie.getTitle());

        ImageView favorite = view.findViewById(R.id.Favorite);
        favorite.setImageResource(mMovie.isFavorite() ? R.drawable.ic_favorited_true : R.drawable.ic_favorite_false);
        favorite.setTooltipText(mMovie.isFavorite() ? "Favorited" : "Not favorited");

        RatingBar rating = view.findViewById(R.id.Rating);
        rating.setRating(mMovie.getRating());

        TextView ratingValue = view.findViewById(R.id.RatingValue);
        ratingValue.setText("" + mMovie.getRating());

        TextView price = view.findViewById(R.id.Price);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        price.setText(formatter.format(mMovie.getTicketPrice()) + "đ");

        TextView category = view.findViewById(R.id.Category);
        category.setText("Category: " + mMovie.getCategory());

        TextView releaseDate = view.findViewById(R.id.ReleaseDate);
        releaseDate.setText("Release date: " + mMovie.getReleaseDate());

        TextView description = view.findViewById(R.id.Description);
        description.setText(mMovie.getDescription());

        commenter = view.findViewById(R.id.Commenter);
        commentInput = view.findViewById(R.id.WriteComments);

        Button commentSubmit = view.findViewById(R.id.SubmitButton);
        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        //endregion

        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent booking = new Intent(Intent.ACTION_VIEW);
                booking.setData(Uri.parse(mMovie.getBookingUrl()));
                startActivity(booking);
            }
        });

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement Add to favorite functionality
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

    private void submitComment(String name, String content, String dateTime) {
        Comment comment = new Comment(name, content, dateTime);
        mMovie.addComment(comment);
        adapter.notifyDataSetChanged();
    }
}