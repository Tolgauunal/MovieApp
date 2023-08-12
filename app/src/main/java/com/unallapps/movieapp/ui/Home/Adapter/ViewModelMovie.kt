package com.unallapps.movieapp.ui.Home.Adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.Data.state.ImageState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelMovie : ViewModel() {


    private val _getImageSlider: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getImageSlider: StateFlow<ImageState> = _getImageSlider

    private val _getImageBottomSlider: MutableStateFlow<ImageState> =
        MutableStateFlow(ImageState.Idle)
    val getImageBottomSlider: StateFlow<ImageState> = _getImageBottomSlider

    private val _getMostLike: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getMostLike: StateFlow<ImageState> = _getMostLike

    private val _getMostWatch: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getMostWatch: StateFlow<ImageState> = _getMostWatch

    private val _getLastMovie: MutableStateFlow<ImageState> = MutableStateFlow(ImageState.Idle)
    val getLastMovie: StateFlow<ImageState> = _getLastMovie

    fun getImageSlider(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                delay(1000)
                _getImageSlider.value = ImageState.Result(movies)
            }
        }
    }

    fun getImageBottomSlider(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getImageSlider.value = ImageState.Loading
                delay(2500)
                _getImageBottomSlider.value = ImageState.Result(movies)
            }
        }
    }

    fun getMostLiked(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getImageSlider.value = ImageState.Loading
                delay(1500)
                _getMostLike.value = ImageState.Result(movies)
            }
        }
    }

    fun getMostWatched(movies: List<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getImageSlider.value = ImageState.Loading
                delay(2000)
                _getMostWatch.value = ImageState.Result(movies)
            }
        }
    }

    fun getLastMovie(movies: MutableList<Movie>) {
        viewModelScope.launch {
            movies.let {
                _getImageSlider.value = ImageState.Loading
                delay(3000)
                _getLastMovie.value = ImageState.Result(movies)
            }
        }
    }
}