package com.unallapps.movieapp.Data.state

import android.util.Log

sealed class LoginState {
    object Idle : LoginState()
    class Error(val error: String) : LoginState()
    class Result(val email: String) : LoginState()
    object Loading : LoginState()
}
