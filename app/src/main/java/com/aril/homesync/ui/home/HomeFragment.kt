package com.aril.homesync.ui.home

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aril.homesync.R
import com.aril.homesync.data.model.device.Device
import com.aril.homesync.data.model.device.DeviceResponse
import com.aril.homesync.data.model.device.UpdateDeviceResponse
import com.aril.homesync.data.repository.DeviceRepository
import com.aril.homesync.databinding.FragmentHomeBinding
import com.aril.homesync.ui.adapter.DeviceAdapter
import com.aril.homesync.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    private lateinit var repository : DeviceRepository

    private lateinit var adapter: DeviceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        repository = DeviceRepository()

        setUpGreeting()
        setupUser()
        loadDevices()
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

    private fun loadDevices() {
        repository.getDevices().enqueue(object: Callback<DeviceResponse> {
            override fun onResponse(
                call: Call<DeviceResponse>,
                response: Response<DeviceResponse>
            ) {
                if (_binding == null) return

                if (response.isSuccessful && response.body() != null) {
                    val devices = response.body()!!.data
                    setupRecyclerView(devices.toMutableList())
                    updateSummary(devices)
                }
            }

            override fun onFailure(
                call: Call<DeviceResponse>,
                response: Throwable
            ) {
                Toast.makeText(
                    requireContext(),
                    "Failed to load devices",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    private fun setupRecyclerView(devices: MutableList<Device>) {

        adapter = DeviceAdapter(devices) { device, isChecked ->

            val status = if (isChecked) "ON" else "OFF"

            updateDevice(device, status)
        }

        binding.rvDevices.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDevices.adapter = adapter
    }

    private fun updateSummary(devices: List<Device>) {

        val online = devices.count {
            it.status.equals("ON", true)
        }

        val offline = devices.count {
            it.status.equals("OFF", true)
        }

        val light = devices.count {
            it.type.equals("Light", true)
        }

        val camera = devices.count {
            it.type.equals("Camera", true)
        }

        val sensor = devices.count {
            it.type.equals("Sensor", true)
        }

        val plug = devices.count {
            it.type.equals("Smart Plug", true)
        }

        binding.tvOnline.text = online.toString()
        binding.tvOffline.text = offline.toString()

        binding.tvLightCount.text = light.toString()
        binding.tvCameraCount.text = camera.toString()
        binding.tvSensorCount.text = sensor.toString()
        binding.tvPlugCount.text = plug.toString()
    }

    private fun updateDevice(device: Device, status: String) {

        repository.updateDevice(device.id, status)
            .enqueue(object : Callback<UpdateDeviceResponse> {

                override fun onResponse(
                    call: Call<UpdateDeviceResponse>,
                    response: Response<UpdateDeviceResponse>
                ) {

                    if (response.isSuccessful) {

                        val updated = device.copy(
                            status = status
                        )

                        adapter.updateDevice(updated)
                    }
                }

                override fun onFailure(
                    call: Call<UpdateDeviceResponse>,
                    t: Throwable
                ) {

                }
            })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

