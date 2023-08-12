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
import com.example.viewmodel3.Movie
import com.example.viewmodel3.movies
import com.example.viewmodel3.showAlert
import com.unallapps.movieapp.Data.state.ImageState
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.BestMovieMainBinding
import com.unallapps.movieapp.ui.Home.Adapter.ImageSliderBottomAdapter
import com.unallapps.movieapp.ui.Home.Adapter.ImageSliderTopAdapter
import com.unallapps.movieapp.ui.Home.Adapter.RecylerviewMovieAdapter
import com.unallapps.movieapp.ui.Home.Adapter.ViewModelmageSlider
import com.unallapps.movieapp.ui.Login.LoginActivity
import com.unallapps.movieapp.ui.Login.LoginActivity.Constants.SHARED_PREF_NAME
import com.unallapps.movieapp.ui.MovieDetail.MovieDetail
import com.unallapps.movieapp.ui.Search.SearchActivity
import com.unallapps.movieapp.ui.VideoActivity
import kotlinx.coroutines.launch

class HomeMovieActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_KEY = "MOVIE_KEY"
    }

    private lateinit var binding: BestMovieMainBinding
    private val viewModel: ViewModelmageSlider by viewModels()
    lateinit var viewPagerAdapter: ImageSliderTopAdapter
    lateinit var bestMovieAdapter: RecylerviewMovieAdapter
    lateinit var mostMovieAdapter: RecylerviewMovieAdapter
    lateinit var lastMovieAdapter: RecylerviewMovieAdapter
    private lateinit var movieAdapter: ImageSliderBottomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BestMovieMainBinding.inflate(layoutInflater) //binding yapısı tanımladık
        setContentView(binding.root) //içeriği binding yaptık


        //Viewmodele movieleri gönderiyoruz
        viewModel.getImageSlider(movies)
        viewModel.getMostLiked(movies)
        viewModel.getMostWatched(movies)
        viewModel.getLastMovie(movies)
        viewModel.getImageBottomSlider(movies)



        listeners()
        observedImageTopSlider()
        observedGetMostLiked()
        obsevedGetMostWatched()
        obsevedLastMovie()
        obsevedImageBottomSlider()


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
            val sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.imgSearch.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }

    }

    //Tab ImageSlider Lifecyle
    private fun observedImageTopSlider() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImageSlider.collect {
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            binding.idViewPager.isVisible = true
                            binding.topImageProgress.isVisible = false
                            viewPagerAdapter =
                                ImageSliderTopAdapter(
                                    this@HomeMovieActivity,
                                    it.movie,
                                    this@HomeMovieActivity::onClick,
                                    this@HomeMovieActivity::playCLick
                                )
                            binding.idViewPager.adapter = viewPagerAdapter
                        }
                        is ImageState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun playCLick(movie: Movie) {
        val intent =
            Intent(this@HomeMovieActivity, VideoActivity::class.java)
        intent.putExtra("videoLink", movie.videoLink)
        startActivity(intent)
    }

    private fun onClick(movie: Movie) {
        val intent =
            Intent(this@HomeMovieActivity, MovieDetail::class.java)
        intent.putExtra(MOVIE_KEY, movie)
        startActivity(intent)
    }

    //En Çok izlenenler Lifecyle
    private fun obsevedGetMostWatched() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMostWatch.collect { it ->
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            binding.rvMostWatched.isVisible = true
                            binding.mostWatchProgress.isVisible = false
                            mostMovieAdapter = RecylerviewMovieAdapter(
                                this@HomeMovieActivity,
                                it.movie.sortedByDescending { it.count }) {
                                val intent = Intent(this@HomeMovieActivity, MovieDetail::class.java)
                                intent.putExtra(MOVIE_KEY, it)
                                startActivity(intent)
                            }//izlenme sayısına gör Sıralama işlemi Yaptık
                            binding.rvMostWatched.adapter = mostMovieAdapter
                        }
                        is ImageState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }
                        else -> {}
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
                            binding.rvMostLiked.isVisible = true
                            binding.mostLikeProgress.isVisible = false
                            bestMovieAdapter = RecylerviewMovieAdapter(
                                this@HomeMovieActivity,
                                it.movie.sortedByDescending { it.imdb }) {
                                val intent = Intent(this@HomeMovieActivity, MovieDetail::class.java)
                                intent.putExtra(MOVIE_KEY, it)
                                startActivity(intent)
                            }//Imdb puanına göre Sıralama işlemi Yaptık
                            binding.rvMostLiked.adapter = bestMovieAdapter
                        }
                        is ImageState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun obsevedImageBottomSlider() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImageBottomSlider.collect {
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Result -> {
                            binding.imageSliderBottom.isVisible = true
                            binding.imgSliderBtProgress.isVisible = false
                            binding.indicator.isVisible = true
                            movieAdapter =
                                ImageSliderBottomAdapter(this@HomeMovieActivity, it.movie) {
                                    val intent =
                                        Intent(this@HomeMovieActivity, MovieDetail::class.java)
                                    intent.putExtra(MOVIE_KEY, it)
                                    startActivity(intent)
                                }
                            binding.imageSliderBottom.adapter = movieAdapter
                            binding.indicator.setViewPager(binding.imageSliderBottom)

                        }

                        is ImageState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    //Son Eklenenler Lifecyle
    private fun obsevedLastMovie() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLastMovie.collect {
                    when (it) {
                        is ImageState.Idle -> {}
                        is ImageState.Loading -> {}
                        is ImageState.Result -> {
                            binding.rvLastWatched.isVisible = true
                            binding.lastMovieProgress.isVisible = false
                            lastMovieAdapter = RecylerviewMovieAdapter(
                                this@HomeMovieActivity,
                                it.movie.sortedByDescending { it.year }) {
                                val intent = Intent(this@HomeMovieActivity, MovieDetail::class.java)
                                intent.putExtra(MOVIE_KEY, it)
                                startActivity(intent)
                            }
                            binding.rvLastWatched.adapter = lastMovieAdapter
                        }

                        is ImageState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }
                    }
                }
            }
        }
    }


}