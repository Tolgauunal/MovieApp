package com.unallapps.movieapp.ui.Home.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.ImageSliderBottomBinding

@Suppress("UNREACHABLE_CODE")
class ImageSliderBottomAdapter(
    val context: Context, val imageList: List<Movie>, val onClick: (movie: Movie) -> Unit
) :
    RecyclerView.Adapter<ImageSliderBottomAdapter.CustomView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val binding = ImageSliderBottomBinding.inflate(LayoutInflater.from(context), parent, false)
        //binding.root.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return CustomView(binding)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val imagelist = imageList[position]
        holder.movieName.text = context.getString(imagelist.name)
        holder.detail.text = " ${imagelist.year} | ${imagelist.category1} | ${imagelist.producer}"
        holder.photo.load(imagelist.photo)
        holder.bannerImg.load(imagelist.banner)
        holder.itemView.setOnClickListener {
            onClick(imagelist)
        }
    }

    inner class CustomView(binding: ImageSliderBottomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val bannerImg = binding.vpImgBanner
        val photo = binding.vpImgPhoto
        val movieName = binding.vpTxtMovieName
        val detail = binding.vpTxtMovieDetail
        fun setData(imageUrl: String) {

            Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.background_login)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photo)
        }
    }

}