package com.aril.homesync.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aril.homesync.R
import com.aril.homesync.data.model.device.Device
import com.aril.homesync.databinding.ItemDeviceBinding

class DeviceAdapter(
    private val listDevice: MutableList<Device>,
    private val onSwitchClick: (Device, Boolean) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = listDevice.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val device = listDevice[position]

        holder.binding.apply {

            tvDeviceName.text = device.name
            tvLocation.text = device.location

            imgDevice.setImageResource(
                when (device.icon) {
                    "light" -> R.drawable.light
                    "camera" -> R.drawable.camera
                    "plug" -> R.drawable.smart_plug
                    "sensor" -> R.drawable.sensor
                    "lock" -> R.drawable.lock
                    else -> R.drawable.devices
                }
            )

            switchDevice.setOnCheckedChangeListener(null)

            switchDevice.isChecked = device.status == "ON"

            switchDevice.setOnCheckedChangeListener { _, isChecked ->
                onSwitchClick(device, isChecked)
            }
        }
    }

    fun updateDevice(updatedDevice: Device) {

        val index = listDevice.indexOfFirst {
            it.id == updatedDevice.id
        }

        if (index != -1) {
            listDevice[index] = updatedDevice
            notifyItemChanged(index)
        }
    }
}