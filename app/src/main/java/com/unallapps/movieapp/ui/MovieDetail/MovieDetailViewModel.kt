package com.unallapps.movieapp.ui.MovieDetail

import androidx.lifecycle.ViewModel
import com.example.viewmodel3.Actor
import com.unallapps.movieapp.Data.state.AdapterState
import com.unallapps.movieapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailViewModel : ViewModel() {
    private val _adapterState: MutableStateFlow<AdapterState> = MutableStateFlow(AdapterState.Idle)
    val adapterState: StateFlow<AdapterState> = _adapterState

    fun getActor(actor: List<Actor>) {
        kotlin.runCatching {
            actor?.let {
                _adapterState.value = AdapterState.ActorResult(actor)
            }
        }.onFailure {
            _adapterState.value = AdapterState.Error(R.string.actorList)
        }
    }

}