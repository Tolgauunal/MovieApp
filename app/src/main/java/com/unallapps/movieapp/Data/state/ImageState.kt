package com.unallapps.movieapp.Data.state

import com.example.viewmodel3.Movie

sealed class ImageState {
    object Idle : ImageState()
    class Result(val movie: List<Movie>) : ImageState()
}