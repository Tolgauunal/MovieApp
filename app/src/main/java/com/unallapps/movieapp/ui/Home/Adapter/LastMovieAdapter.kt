package com.unallapps.movieapp.ui.Home.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.Data.model.Images
import com.unallapps.movieapp.databinding.LastMovieItemBinding

class LastMovieAdapter(
    val context: Context,
    val images: List<Movie>
) :
    RecyclerView.Adapter<LastMovieAdapter.MyViewHolder>() {

    class MyViewHolder(private val view: LastMovieItemBinding) : ViewHolder(view.root) {
        val ivMovies = view.ivLastMoviewItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LastMovieItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = images[position]
        holder.ivMovies.load(image.photo)
    }

    override fun getItemCount(): Int {
        return 5
    }


}