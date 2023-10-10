package com.example.cinema.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.MainActivity;
import com.example.cinema.Movies.Movie;
import com.example.cinema.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {


    Context context;
    LayoutInflater inflater;

    public FavoriteAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_layout,parent,false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.update(MainActivity.movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return MainActivity.movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FavoriteAdapter adapter;
        TextView tvName;
        ImageView ivItem;

        public ViewHolder(@NonNull View itemView, FavoriteAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            tvName = itemView.findViewById(R.id.tv_name);
            ivItem = itemView.findViewById(R.id.iv_item);
        }

        public void update(Movie m) {

            tvName.setText(m.getTitle());
            ivItem.setImageDrawable(m.getThumbnail());
        }
    }
}
