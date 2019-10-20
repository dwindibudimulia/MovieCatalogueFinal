package com.example.dwindibudimulia.moviecatalogue.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.activity.MovieDetailActivity;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> movies = new ArrayList<>();

    public void setMovies(ArrayList<Movie> itemMovie) {
        if (movies.size() > 0) {
            this.movies.clear();
        }
        this.movies.addAll(itemMovie);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }


    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int i) {
        holder.onBind(movies.get(i));
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgPhoto;
        private TextView txtName;
        private TextView txtRating;
        private TextView txtDate;
        private TextView txtGenre;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            txtName = itemView.findViewById(R.id.txt_name);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtGenre = itemView.findViewById(R.id.txt_genre);
            itemView.setOnClickListener(this);
        }

        void onBind(Movie item) {
            String url_photo = item.getPosterPath();
            txtName.setText(item.getnameMovie());
            txtDate.setText(item.getdateMovie());
            txtRating.setText(String.valueOf(item.getRatingMovie()));
            txtGenre.setText(item.getgenreMovie());
            Glide.with(itemView)
                    .load(url_photo)
                    .into(imgPhoto);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Movie movie = movies.get(position);

            Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movies.get(position));
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            ((Activity) itemView.getContext()).startActivityForResult(intent, MovieDetailActivity.REQUEST_UPDATE);
        }
    }


    public void addItem(Movie movie) {
        this.movies.add(movie);
        notifyItemInserted(movies.size() - 1);
    }

    public void removeItem(int position) {
        this.movies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movies.size());
    }
}
