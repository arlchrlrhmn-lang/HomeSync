package com.aril.homesync.ui.home

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aril.homesync.R
import com.aril.homesync.databinding.FragmentHomeBinding
import com.aril.homesync.utils.SessionManager


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        sessionManager = SessionManager(requireContext())

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

