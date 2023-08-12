package com.unallapps.movieapp.ui.Search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.Data.state.AdapterState
import com.unallapps.movieapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _adapterState: MutableStateFlow<AdapterState> = MutableStateFlow(AdapterState.Idle)
    val adapterState: StateFlow<AdapterState> = _adapterState

    fun getMovie(movies: MutableList<Movie>) {
        viewModelScope.launch {
            if (movies.isEmpty()) {
                _adapterState.value = AdapterState.Error(R.string.veriYuklenmedi)
            } else {
                _adapterState.value = AdapterState.Result(movies)
            }
        }
    }


}