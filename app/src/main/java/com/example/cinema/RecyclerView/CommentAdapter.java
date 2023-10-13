package com.example.cinema.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.Movies.Comment;
import com.example.cinema.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    ArrayList<Comment> data;

    public CommentAdapter(Context context, ArrayList<Comment> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.commenter.setText(data.get(i).commenter);
        holder.content.setText(data.get(i).content);
        holder.dateTime.setText(data.get(i).dateTime);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView commenter;
        TextView content;
        TextView dateTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commenter = itemView.findViewById(R.id.Name);
            content = itemView.findViewById(R.id.Comment);
            dateTime = itemView.findViewById(R.id.CommentDate);
        }
    }
}
