package com.aril.homesync.data.repository

import com.aril.homesync.api.ApiClient
import com.aril.homesync.data.model.device.DeviceResponse
import com.aril.homesync.data.model.device.UpdateDeviceRequest
import com.aril.homesync.data.model.device.UpdateDeviceResponse
import retrofit2.Call

class DeviceRepository {

    fun getDevices(): Call<DeviceResponse> {
        return ApiClient.apiService.getDevices()
    }

    fun updateDevice(
        id: Int,
        status: String
    ): Call<UpdateDeviceResponse> {

        return ApiClient.apiService.updateDevice(
            id,
            UpdateDeviceRequest(status)
        )
    }
}
