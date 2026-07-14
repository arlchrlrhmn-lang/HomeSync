package com.aril.homesync.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aril.homesync.R
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordRequest
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordResponse
import com.aril.homesync.data.repository.AuthRepository
import com.aril.homesync.databinding.ActivityForgotPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val repository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListener()
    }

    private fun setUpListener() {

        binding.btnSend.setOnClickListener {
            if (validateInput()) {
                forgotPassword()
            }
        }
        binding.tvBack.setOnClickListener {
            finish()
        }
    }

    private fun validateInput(): Boolean {

        val email = binding.etEmail.text.toString().trim()

        var isValid = true

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email cannot be empty"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }
        if (email.isNotEmpty() &&
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ){
            binding.tilEmail.error = "Invalid email format"
            isValid = false
        }
        return isValid
    }

    private fun forgotPassword() {

        val request = ForgotPasswordRequest(
            email = binding.etEmail.text.toString().trim()
        )

        repository.forgotPassword(request)
           .enqueue(object: Callback<ForgotPasswordResponse> {
                override fun onResponse(
                    call: Call<ForgotPasswordResponse>,
                    response: Response<ForgotPasswordResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {

                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Email is not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ForgotPasswordResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
           })
    }
}

