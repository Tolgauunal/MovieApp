package com.unallapps.movieapp.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodel3.showAlert
import com.example.viewmodel3.showToast
import com.unallapps.movieapp.Data.state.LoginState
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.ActivityLoginBinding
import com.unallapps.movieapp.ui.HomeActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        observeLogin()
        observeSignUp()
    }

    private fun observeSignUp() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signState.collect {
                    when (it) {
                        is LoginState.Idle -> {}
                        is LoginState.Loading -> {}
                        is LoginState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }

                        is LoginState.Result -> {
                            showToast("Kayıt Başarılı Hoşgeldiniz ${it.email}")
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
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
                            showToast("Hoşgeldiniz ${it.email}")
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }

                        is LoginState.Error -> {
                            showAlert("Hata", it.error, R.drawable.loginalert)
                        }
                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.editTextEmail.text.toString(),
                binding.editTextPass.text.toString(),
            )
        }
        binding.btnSignUp.setOnClickListener {
            if (binding.etPasswordRepeat.isVisible) {
                viewModel.signUp(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPass.text.toString(),
                    binding.etPasswordRepeat.text.toString()
                )
            }
            binding.etPasswordRepeat.isVisible = true
        }
    }
}