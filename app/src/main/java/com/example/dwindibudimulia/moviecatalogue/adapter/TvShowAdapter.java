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
import com.example.dwindibudimulia.moviecatalogue.activity.TvShowDetailActivity;
import com.example.dwindibudimulia.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private ArrayList<TvShow> tvShows = new ArrayList<>();

    public void setTvShow(ArrayList<TvShow> itemtvShow) {
        if (tvShows.size() > 0) {
            tvShows.clear();
        }
        tvShows.addAll(itemtvShow);
        notifyDataSetChanged();
    }

    public ArrayList<TvShow> getTvShows() {
        return tvShows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv_show, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder holder, int i) {
        holder.onBind(tvShows.get(i));
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ImgPhotoTv;
        private TextView txtNameTv;
        private TextView txtRatingTv;
        private TextView txtDateTv;
        private TextView txtGenreTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgPhotoTv = itemView.findViewById(R.id.img_photo_tv);
            txtNameTv = itemView.findViewById(R.id.txt_name_tv);
            txtRatingTv = itemView.findViewById(R.id.txt_rating_tv);
            txtDateTv = itemView.findViewById(R.id.txt_date_tv);
            txtGenreTv = itemView.findViewById(R.id.txt_genre_tv);
            itemView.setOnClickListener(this);
        }

        void onBind(TvShow tvShow) {
            txtNameTv.setText(tvShow.getnameTv());
            txtRatingTv.setText(String.valueOf(tvShow.getRatingTv()));
            txtDateTv.setText(tvShow.getdateTv());
            txtGenreTv.setText(tvShow.getgenreTv());
            Glide.with(itemView)
                    .load(tvShow.getPhotoTv())
                    .into(ImgPhotoTv);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            TvShow tvShow = tvShows.get(position);

            Intent intent = new Intent(itemView.getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.EXTRA_POSITION, position);
            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShows.get(position));
            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShow);
            ((Activity) itemView.getContext()).startActivityForResult(intent, TvShowDetailActivity.REQUEST_UPDATE);
        }
    }

    public void addItem(TvShow tvShow) {
        this.tvShows.add(tvShow);
        notifyItemInserted(tvShows.size() - 1);
    }

    public void removeItem(int position) {
        this.tvShows.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tvShows.size());
    }
}
