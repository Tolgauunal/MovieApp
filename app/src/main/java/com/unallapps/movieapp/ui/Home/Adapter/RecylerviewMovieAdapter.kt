package com.unallapps.movieapp.ui.Home.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.LastMovieItemBinding

class RecylerviewMovieAdapter(
    val context: Context,
    var images: List<Movie>,
    val onClick: (clickList: Movie) -> Unit
) :
    RecyclerView.Adapter<RecylerviewMovieAdapter.MyViewHolder>() {

    class MyViewHolder(view: LastMovieItemBinding) : ViewHolder(view.root) {
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

    fun updateList(filteredList: List<Movie>) {
        images = filteredList
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = images[position]
        holder.ivMovies.load(image.photo)

        holder.itemView.setOnClickListener {
            onClick(image)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }


}