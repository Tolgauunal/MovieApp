package com.unallapps.movieapp.ui.MovieDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.viewmodel3.Actor
import com.unallapps.movieapp.databinding.ItemActorRecylerviewBinding

class MovieDetailRecylerViewAdapter(val context: Context, val actorList: List<Actor>) :
    RecyclerView.Adapter<MovieDetailRecylerViewAdapter.CustomView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return CustomView(
            ItemActorRecylerviewBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val actorList = actorList[position]
        holder.ivActorImage.load(actorList.photo)
        holder.txtActorName.text = actorList.name
        holder.txtMovieActorName.text = actorList.actorName
    }

    class CustomView(binding: ItemActorRecylerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivActorImage = binding.ivProfileTwo
        val txtActorName = binding.tvRealName
        val txtMovieActorName = binding.tvMovieName
    }

}