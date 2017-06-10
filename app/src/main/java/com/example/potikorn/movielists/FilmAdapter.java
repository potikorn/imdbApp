package com.example.potikorn.movielists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.potikorn.movielists.dao.FilmModel;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    private final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    private List<FilmModel> films;
    private OnFilmClickListener onFilmClickListener;
    private Context context;
    private String popularityAndPublished;

    public FilmAdapter(List<FilmModel> films, OnFilmClickListener onFilmClickListener, Context context) {
        this.films = films;
        this.onFilmClickListener = onFilmClickListener;
        this.context = context;
    }

    public void setFilms(List<FilmModel> films) {
        this.films = films;
        notifyDataSetChanged();
    }

    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_item, parent, false);
        return new FilmHolder(view, onFilmClickListener);
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        FilmModel item = films.get(position);
        holder.film = item;
        holder.title.setText(item.getTitle());

        popularityAndPublished = String.format("%.1f/10  :  %s", item.getVoteAverage(), item.getReleaseDate());
        holder.releaseAndPopular.setText(popularityAndPublished);

        holder.overview.setText(item.getOverview());

        String imageUrl = BASE_IMAGE_URL + item.getPosterPath();
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(holder.poster) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (films == null) {
            return 0;
        } else {
            return films.size();
        }
    }

    static class FilmHolder extends RecyclerView.ViewHolder {

        FilmModel film;
        TextView title;
        TextView overview;
        TextView releaseAndPopular;
        ImageView poster;

        FilmHolder(View itemView, final OnFilmClickListener onFilmClickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            overview = (TextView) itemView.findViewById(R.id.tv_overview);
            releaseAndPopular = (TextView) itemView.findViewById(R.id.tv_published_year);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFilmClickListener.onFilmClick(film);
                }
            });
        }

    }

    public interface OnFilmClickListener {
        void onFilmClick(FilmModel film);
    }

}
