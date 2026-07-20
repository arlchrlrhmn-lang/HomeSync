package com.aril.homesync.data.repository

import com.aril.homesync.api.ApiClient
import com.aril.homesync.data.model.device.DeviceResponse
import retrofit2.Call

class DeviceRepository {

    fun getDevices(): Call<DeviceResponse> {
        return ApiClient.apiservice.getDevices()
    }
}
