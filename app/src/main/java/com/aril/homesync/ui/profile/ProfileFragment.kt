package com.aril.homesync.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aril.homesync.R
import com.aril.homesync.databinding.FragmentProfileBinding
import com.aril.homesync.ui.auth.LoginActivity
import com.aril.homesync.utils.SessionManager
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setupProfile()
        setupListener()
    }

    private fun setupListener() {

        binding.btnLogout.setOnClickListener {

            sessionManager.logout()

            startActivity(
                Intent(requireContext(), LoginActivity::class.java)
            )

            requireActivity().finish()
        }
    }

    private fun setupProfile() {

        binding.tvName.text = sessionManager.getName()
        binding.tvEmail.text = sessionManager.getEmail()
        binding.tvRole.text = sessionManager.getRole()

        Glide.with(requireContext())
            .load(sessionManager.getPhoto())
            .placeholder(R.drawable.aril)
            .error(R.drawable.ic_profile)
            .circleCrop()
            .into(binding.ivProfile)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
