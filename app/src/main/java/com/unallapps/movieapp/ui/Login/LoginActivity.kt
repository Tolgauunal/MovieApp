package com.unallapps.movieapp.ui.Login

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodel3.showAlert
import com.example.viewmodel3.showToast
import com.unallapps.movieapp.Data.Database.users
import com.unallapps.movieapp.Data.state.LoginState
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.ActivityLoginBinding
import com.unallapps.movieapp.ui.Home.HomeMovieActivity
import com.unallapps.movieapp.ui.Login.LoginActivity.Constants.REMEMBER_ME_KEY
import com.unallapps.movieapp.ui.Login.LoginActivity.Constants.SHARED_PREF_NAME
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    object Constants {
        const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        const val REMEMBER_ME_KEY = "remember_me_key"
    }

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    var rememberMe: Boolean = false
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        observeLogin()
        observeSignUp()
        rememberMeControl()
        editTextControl()
    }

    private fun rememberMeControl() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        rememberMe = sharedPreferences.getBoolean(REMEMBER_ME_KEY, false)
        if (rememberMe) {
            val intent = Intent(this, HomeMovieActivity::class.java)
            startActivity(intent)
        }
        binding.checkBoxRememberMe.setOnCheckedChangeListener { buttonView, isChecked ->
            rememberMe = isChecked
        }
    }

    private fun editTextControl() {
        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // İçerisinde değer yoksa hint black
                binding.textInputEmail.hintTextColor = ColorStateList.valueOf(Color.BLACK)
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //edittex içerisinde veri varsa yapılacaklar
                if (start >= 1) {
                    val email = s.toString()
                    if (isEmailAvailable(email)) {
                        binding.textInputEmail.hintTextColor = ColorStateList.valueOf(Color.BLACK)
                    } else {
                        binding.textInputEmail.hintTextColor = ColorStateList.valueOf(Color.RED)
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.editTextPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.textInputLayout.hintTextColor = ColorStateList.valueOf(Color.BLACK)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start >= 1) {
                    val password = s.toString()
                    if (password.length >= 8) {
                        binding.textInputLayout.hintTextColor = ColorStateList.valueOf(Color.BLACK)
                    } else {
                        binding.textInputLayout.hintTextColor = ColorStateList.valueOf(Color.RED)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun isEmailAvailable(email: String): Boolean {
        val existingUsers = users
        return existingUsers.any { it.email == email }

    }

    private fun observeSignUp() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signState.collect {
                    when (it) {
                        is LoginState.Idle -> {}
                        is LoginState.Loading -> {}
                        is LoginState.Error -> {
                            showAlert(
                                getString(R.string.errorAtLogin),
                                getString(it.error),
                                R.drawable.loginalert
                            )
                        }

                        is LoginState.Result -> {
                            sharedPreferences.edit()
                                .putBoolean(REMEMBER_ME_KEY, rememberMe)
                                .apply()
                            showToast(getString(R.string.successSignUp) + " ${it.email}")
                            val intent = Intent(this@LoginActivity, HomeMovieActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun observeLogin() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect {
                    when (it) {
                        is LoginState.Idle -> {}
                        is LoginState.Loading -> {}
                        is LoginState.Result -> {
                            showToast(getString(R.string.loginWeolcome) + " ${it.email}")
                            sharedPreferences.edit()
                                .putBoolean(REMEMBER_ME_KEY, rememberMe)
                                .apply()
                            val intent = Intent(this@LoginActivity, HomeMovieActivity::class.java)
                            startActivity(intent)
                        }

                        is LoginState.Error -> {
                            showAlert(
                                getString(R.string.errorAtLogin),
                                getString(it.error),
                                R.drawable.loginalert
                            )
                        }

                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            if (binding.passwordRepeat.isVisible) {
                binding.passwordRepeat.isVisible = false
            } else {
                viewModel.login(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPass.text.toString(),
                    rememberMe
                )
            }
        }
        binding.btnSignUp.setOnClickListener {
            if (binding.passwordRepeat.isVisible) {
                viewModel.signUp(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPass.text.toString(),
                    binding.etPasswordRepeat.text.toString(),
                    rememberMe
                )
            }
            binding.passwordRepeat.isVisible = true
        }
    }
}

