package com.unallapps.movieapp.Data.state

import com.example.viewmodel3.Movie

sealed class ImageState {
    object Idle : ImageState()
    object Loading : ImageState()
    class Result(val movie: List<Movie>) : ImageState()
    class Error(val error: String) : ImageState()
}