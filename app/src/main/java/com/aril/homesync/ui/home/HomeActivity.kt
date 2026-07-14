package com.aril.homesync.ui.home

import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aril.homesync.databinding.ActivityHomeBinding
import com.aril.homesync.utils.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setUpGreeting()
        setupUser()
    }

    private fun setUpGreeting() {

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val greeting = when (hour) {
            in 0..11 -> "Good Morning,"
            in 12..16 -> "Good Afternoon,"
            in 17..20 -> "Good Evening,"
            else -> "Good Night,"
        }

        binding.tvGreeting.text = greeting
    }

    private fun setupUser() {

        binding.tvName.text = sessionManager.getName()
    }
}