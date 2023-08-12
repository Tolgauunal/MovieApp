package com.unallapps.movieapp.Data.state

import android.util.Log

sealed class LoginState {
    object Idle : LoginState()
    class Error(val error: Int) : LoginState()
    class Result(val email: String, val rememberMe: Boolean) : LoginState()

    object Loading : LoginState()
}
