package com.example.cinema.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.Fragment.MovieFragment;
import com.example.cinema.MainActivity;
import com.example.cinema.Movies.Movie;
import com.example.cinema.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {


    Context context;
    LayoutInflater inflater;
    ArrayList<Movie> data;

    public FavoriteAdapter(Context context,ArrayList<Movie> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_layout,parent,false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.update(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Movie movie = data.get(getAdapterPosition());
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setMessage("Remove "+movie.getTitle()+" from favorite list?");
                    dialogBuilder.setCancelable(true);
                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (Movie m:MainActivity.movieList){
                                if (m.getTitle().equals(movie.getTitle())){
                                    m.setFavorite(false);
                                }
                            }
                            data.remove(movie);
                            dialog.cancel();
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });
                    dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieFragment movieFragment = MovieFragment.newInstance(data.get(getAdapterPosition()));
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_display, movieFragment).addToBackStack(null).commit();
                }
            });
        }

        public void update(Movie m) {

            tvName.setText(m.getTitle());
            ivItem.setImageDrawable(m.getThumbnail());
        }
    }
}
