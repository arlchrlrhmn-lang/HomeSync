package com.aril.homesync.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aril.homesync.R
import com.aril.homesync.data.model.login.LoginRequest
import com.aril.homesync.data.model.login.LoginResponse
import com.aril.homesync.data.repository.AuthRepository
import com.aril.homesync.databinding.ActivityLoginBinding
import com.google.gson.Gson
import com.aril.homesync.data.model.common.ApiErrorResponse
import com.aril.homesync.ui.main.MainActivity
import com.aril.homesync.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false

    private val gson = Gson()

    private val repository = AuthRepository()

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setUpListener()
        setUpPasswordToggle()
    }

    private fun setUpListener() {

        binding.btnLogin.setOnClickListener {

            if (validateInput()) {
                login()

            }
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )
        }
    }

    private fun validateInput(): Boolean {

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        var isValid = true

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email cannot be empty"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (email.isNotEmpty() &&
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            binding.tilEmail.error = "Invalid email format"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password cannot be empty"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }
        return isValid
    }

    private fun setUpPasswordToggle() {

        binding.tilPassword.setEndIconOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                binding.tilPassword.setEndIconDrawable(R.drawable.view)
            } else {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.tilPassword.setEndIconDrawable(R.drawable.hide)
            }
                binding.etPassword.setSelection(
                    binding.etPassword.text?.length ?: 0)
            }
        }

    private fun login() {

        val request = LoginRequest(
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim()
        )

        repository.login(request).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {

                    val user = response.body()!!.data

                    sessionManager.saveLoginSession(
                        token = user.token,
                        email = user.email,
                        name = user.name,
                        role = user.role,
                        photo = user.photo
                    )

                    startActivity(
                        Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()

                    Toast.makeText(
                        this@LoginActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()

                } else {
                   val errorBody = response.errorBody()?.string()

                    if (errorBody != null) {

                        val errorResponse = gson.fromJson(
                            errorBody,
                            ApiErrorResponse::class.java
                        )
                        Toast.makeText(
                            this@LoginActivity,
                            errorResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
