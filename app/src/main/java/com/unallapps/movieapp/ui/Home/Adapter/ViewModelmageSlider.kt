package com.unallapps.movieapp.ui.Home.Adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.Data.state.ImageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelmageSlider : ViewModel() {


    private val _getImageSlider: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getImageSlider: StateFlow<ImageState> = _getImageSlider

    private val _getMostLike: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getMostLike: StateFlow<ImageState> = _getMostLike

    private val _getMostWatch: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getMostWatch: StateFlow<ImageState> = _getMostWatch

    private val _getLastMovie: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getLastMovie: StateFlow<ImageState> = _getLastMovie

    fun getImageSlider(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getImageSlider.value = ImageState.Result(movies)
            }
        }
    }

    fun getMostLiked(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getMostLike.value = ImageState.Result(movies)
            }
        }
    }

    fun getMostWatched(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getMostWatch.value = ImageState.Result(movies)
            }
        }
    }

    fun getLastMovie(movies: MutableList<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getLastMovie.value = ImageState.Result(movies)
            }
        }
    }
}