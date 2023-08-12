package com.unallapps.movieapp.ui.Search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodel3.Movie
import com.example.viewmodel3.movies
import com.example.viewmodel3.showToast
import com.unallapps.movieapp.Data.state.AdapterState
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.ActivitySearchBinding
import com.unallapps.movieapp.ui.Home.Adapter.RecylerviewMovieAdapter
import com.unallapps.movieapp.ui.Home.HomeMovieActivity
import com.unallapps.movieapp.ui.MovieDetail.MovieDetail
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: RecylerviewMovieAdapter
    private val filteredList = mutableListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        viewModel.getMovie(movies)
        observedGetMovie()

        binding.searchtext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    val filteredList =
                        movies.filter { getString(it.name).contains(s.toString(), true) }
                    adapter.updateList(filteredList)
                } else {
                    adapter.updateList(movies)
                }
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }


    @SuppressLint("RepeatOnLifecycleWrongUsage")
    private fun observedGetMovie() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.adapterState.collect {
                    when (it) {
                        is AdapterState.Idle -> {}
                        is AdapterState.Error -> {
                            showToast(getString(R.string.error))
                        }

                        is AdapterState.Result -> {
                            adapter = RecylerviewMovieAdapter(this@SearchActivity, it.movieSearch) {
                                val intent = Intent(this@SearchActivity, MovieDetail::class.java)
                                intent.putExtra(HomeMovieActivity.MOVIE_KEY,it)
                                startActivity(intent)
                                adapter.updateList(movies)
                            }
                            binding.moviesearchRecyler.adapter = adapter
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.intentIMage.setOnClickListener {
            val intent = Intent(this, HomeMovieActivity::class.java)
            startActivity(intent)
        }
        binding.imgEditClear.setOnClickListener {
            binding.searchtext.text.clear()
        }
        binding.imgCloseKey.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.imgEditClear.windowToken, 0)
        }
    }


}