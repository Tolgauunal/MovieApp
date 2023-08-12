package com.unallapps.movieapp.Data.state

import com.example.viewmodel3.Actor
import com.example.viewmodel3.Movie

sealed class AdapterState {
    object Idle : AdapterState()
    class Error(val error: Int) : AdapterState()
    class Result(val movieSearch: List<Movie>) : AdapterState()
    class ActorResult(val actor: List<Actor>) : AdapterState()
}
