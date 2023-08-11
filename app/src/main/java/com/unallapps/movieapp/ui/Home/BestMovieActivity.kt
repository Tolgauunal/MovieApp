package com.unallapps.movieapp.ui.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodel3.movies
import com.unallapps.movieapp.Data.model.Images
import com.unallapps.movieapp.Data.state.ImageState
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.BestMovieMainBinding
import com.unallapps.movieapp.ui.Home.Adapter.BestMovieAdapter
import com.unallapps.movieapp.ui.Home.Adapter.ImageSliderAdapter
import com.unallapps.movieapp.ui.Home.Adapter.LastMovieAdapter
import com.unallapps.movieapp.ui.Home.Adapter.MostMovieAdapter
import com.unallapps.movieapp.ui.Home.Adapter.ViewModelmageSlider
import com.unallapps.movieapp.ui.Login.LoginActivity
import com.unallapps.movieapp.ui.SearchActivity
import kotlinx.coroutines.launch

class BestMovieActivity : AppCompatActivity() {

    val imageData = listOf(
        Images(1, R.drawable.bes),
        Images(1, R.drawable.bir),
        Images(1, R.drawable.iki),
        Images(1, R.drawable.dort),
    )

    private lateinit var binding: BestMovieMainBinding
    private val viewModel: ViewModelmageSlider by viewModels()
    lateinit var viewPagerAdapter: ImageSliderAdapter
    lateinit var bestMovieAdapter: BestMovieAdapter
    lateinit var mostMovieAdapter: MostMovieAdapter
    lateinit var lastMovieAdapter: LastMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BestMovieMainBinding.inflate(layoutInflater) //binding yapısı tanımladık
        setContentView(binding.root) //içeriği binding yaptık


        viewModel.getImageSlider(movies)
        viewModel.getMostLiked(movies)
        viewModel.getMostWatched(movies)
        viewModel.getLastMovie(movies)

        listeners()


        observedImageSlider()
        observedGetMostLiked()
        obsevedGetMostWatched()
        obsevedLastMovie()
    }

    private fun listeners() {
        binding.imgMenu.setOnClickListener {
            if (binding.linearShowMenu.visibility == View.GONE) {
                binding.linearShowMenu.visibility = View.VISIBLE
            } else {
                binding.linearShowMenu.visibility = View.GONE
            }

        }
        binding.menuTxtEXit.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.imgSearch.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }
    }

    //Son Eklenenler Lifecyle
    private fun obsevedLastMovie() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLastMovie.collect {
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            lastMovieAdapter = LastMovieAdapter(
                                this@BestMovieActivity,
                                it.movie.sortedByDescending { it.year })
                            binding.rvLastWatched.adapter = lastMovieAdapter
                        }
                    }
                }
            }
        }
    }


    //En Çok izlenenler Lifecyle
    private fun obsevedGetMostWatched() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMostWatch.collect { it ->
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            mostMovieAdapter = MostMovieAdapter(
                                this@BestMovieActivity,
                                it.movie.sortedByDescending { it.count })//izlenme sayısına gör Sıralama işlemi Yaptık
                            binding.rvMostWatched.adapter = mostMovieAdapter
                        }
                    }
                }
            }
        }
    }

    //En Çok Beğenilenler Lifecyle
    private fun observedGetMostLiked() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMostLike.collect {
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            bestMovieAdapter = BestMovieAdapter(
                                this@BestMovieActivity,
                                it.movie.sortedByDescending { it.imdb })//Imdb puanına göre Sıralama işlemi Yaptık
                            binding.rvMostLiked.adapter = bestMovieAdapter
                        }
                    }
                }
            }
        }
    }

    //Tab ImageSlider Lifecyle
    private fun observedImageSlider() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImageSlider.collect {
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            viewPagerAdapter = ImageSliderAdapter(this@BestMovieActivity, it.movie)
                            binding.idViewPager.adapter = viewPagerAdapter
                        }
                    }
                }
            }
        }
    }
}