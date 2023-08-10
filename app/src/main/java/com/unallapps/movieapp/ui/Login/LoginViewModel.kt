package com.unallapps.movieapp.ui.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unallapps.movieapp.Data.Database
import com.unallapps.movieapp.Data.model.User
import com.unallapps.movieapp.Data.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _signState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val signState: StateFlow<LoginState> = _signState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            if (email.isEmpty() && pass.isEmpty()) {
                _loginState.value = LoginState.Error("Boş Bırakılamaz")
            } else {
                Database.users.firstOrNull { it.email == email && it.password == pass }?.let {
                    _loginState.value = LoginState.Result(email)
                } ?: kotlin.run {
                    _loginState.value = LoginState.Error("Böyle Bir Kullanıcı Yok")
                }
            }
        }

    }

    fun signUp(email: String, pass: String, repeatPass: String) {
        if (email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
            _signState.value = LoginState.Error("Boş Bırakılamaz")
        } else if (!email.isEmpty() && !pass.isEmpty() && !repeatPass.isEmpty()) {
            if (pass.equals(repeatPass)) {
                val user = User(1, email, pass)
                Database.users.add(user)
                _signState.value = LoginState.Result(email)
            } else {
                _signState.value = LoginState.Error("Şifreler Uyumsuz")
            }
        }
    }
}