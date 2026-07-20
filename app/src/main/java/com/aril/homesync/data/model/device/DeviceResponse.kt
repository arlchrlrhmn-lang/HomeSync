package com.aril.homesync.data.model.device

data class DeviceResponse(
    val status: Int,
    val message: String,
    val data: List<Device>
)

