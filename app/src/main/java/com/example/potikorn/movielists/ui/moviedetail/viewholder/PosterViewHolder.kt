package com.example.potikorn.movielists.ui.moviedetail.viewholder

import android.graphics.Bitmap
import android.renderscript.RenderScript
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.example.potikorn.movielists.BASE_IMAGE_PATH
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.extensions.isChangeSelectStated
import com.example.potikorn.movielists.extensions.loadImageView
import com.example.potikorn.movielists.ui.moviedetail.OnActionClickListener
import com.example.potikorn.movielists.widget.BlurProcessor
import kotlinx.android.synthetic.main.item_poster_movie_detail.view.*

class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBindData(film: FilmResult?, callback: OnActionClickListener?) {
        itemView.ivPoster.loadImageView("$BASE_IMAGE_PATH${film?.posterPath}")
        Glide.with(itemView.context)
            .load("$BASE_IMAGE_PATH${film?.posterPath}")
            .asBitmap()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap?,
                    glideAnimation: GlideAnimation<in Bitmap>?
                ) {
                    val blurImage = BlurProcessor(RenderScript.create(itemView.context))
                        .blur(resource, 10f, 2)
                    itemView.ivBackDrop.apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageBitmap(blurImage)
                    }
                }
            })
        itemView.ivIconFavorite.setOnClickListener {
            film?.let { film ->
                it.isChangeSelectStated()
                callback?.onFavoriteClick(FilmResult().apply {
                    id = film.id
                    title = film.title
                    posterPath = film.posterPath
                })
            }
        }
        itemView.ivIconWishList.setOnClickListener { it.isChangeSelectStated() }
        itemView.ivIconRate.setOnClickListener { it.isChangeSelectStated() }
    }
}