package com.unallapps.movieapp.ui.MovieDetail

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.ImageLoader
import coil.load
import com.example.viewmodel3.Actor
import com.example.viewmodel3.Movie
import com.example.viewmodel3.showAlert
import com.example.viewmodel3.showToast
import com.unallapps.movieapp.Data.state.AdapterState
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.ActivityMoviedetailBinding
import com.unallapps.movieapp.ui.Home.HomeMovieActivity
import com.unallapps.movieapp.ui.VideoActivity
import kotlinx.coroutines.launch

class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMoviedetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    lateinit var movieActor: List<Actor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviedetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intentMovie = intent.getParcelableExtra<Movie>(HomeMovieActivity.MOVIE_KEY)
        intentMovie?.let {
            movieActor = intentMovie.actors
            binding.roundedImageView.load(
                intentMovie.banner,
                ImageLoader.Builder(this).allowHardware(false).build()
            )
            binding.tvMovieNameOnVideo.text = getString(intentMovie.name)
            binding.ivIcon1.load(intentMovie.signs1.signs,)
            binding.ivIcon2.load(intentMovie.signs2.signs,)
            binding.tvLikeRate.text = getString(R.string.imdb) + " ${intentMovie.imdb}"
            binding.tvDirector.text = intentMovie.director
            binding.tvAuthor.text = intentMovie.producer
            binding.tvMovieInfo.text = getString(intentMovie.details)
            viewModel.getActor(intentMovie.actors)
        }
        binding.imgShare.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val message = getString(R.string.oneri) + " " + "${intentMovie?.name?.let { it1 -> getString(it1) }}"
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                startActivity(shareIntent)
            } catch (e: ActivityNotFoundException) {
                showToast(getString(R.string.appDoesntFound))
            }
        }
        binding.imgVideoPlay.setOnClickListener {
            val intent=Intent(this,VideoActivity::class.java)
                .putExtra("videoLink", intentMovie?.videoLink)
            startActivity(intent)
        }
        observedGetActor()
    }

    private fun observedGetActor() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.adapterState.collect {
                    when (it) {
                        is AdapterState.ActorResult -> {
                            binding.rvActors.adapter =
                                MovieDetailRecylerViewAdapter(this@MovieDetail, movieActor)
                        }
                        is AdapterState.Error -> {
                            showAlert(
                                getString(R.string.error),
                                getString(R.string.error),
                                R.drawable.loginalert
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}