package com.example.favoriteappcatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.favoriteappcatalogue.R;
import com.example.favoriteappcatalogue.model.Favorite;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final ArrayList<Favorite> list = new ArrayList<>();

    public void setList(ArrayList<Favorite> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRating, tvReleaseDate, tvGenre;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvName = itemView.findViewById(R.id.txt_name);
            tvRating = itemView.findViewById(R.id.txt_rating);
            tvReleaseDate = itemView.findViewById(R.id.txt_date);
            tvGenre = itemView.findViewById(R.id.txt_genre);
        }

        void onBind(Favorite item) {
            String url_photo = item.getPosterPath();
            tvName.setText(item.getName());
            tvReleaseDate.setText(item.getReleasedate());
            tvRating.setText(String.valueOf(item.getRating()));
            tvGenre.setText(item.getGenre());
            Glide.with(itemView)
                    .load(url_photo)
                    .apply(new RequestOptions())
                    .into(imgPhoto);
        }
    }

}
