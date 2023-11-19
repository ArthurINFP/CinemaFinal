package com.example.cinema.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinema.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    ImageView thumbnail;
    TextView title, duration, rating, releaseDate, category;
    Button addToInterest, bookNow, share;

    MovieViewHolder(View view) {
        super(view);
        thumbnail = view.findViewById(R.id.thumbnail);
        title = view.findViewById(R.id.title);
        category = view.findViewById(R.id.category);
        duration = view.findViewById(R.id.duration);
        rating = view.findViewById(R.id.rating);
        releaseDate = view.findViewById(R.id.releaseDate);
        addToInterest = view.findViewById(R.id.add_to_interest);
        bookNow = view.findViewById(R.id.book_now);
        share = view.findViewById(R.id.share);
    }
}
