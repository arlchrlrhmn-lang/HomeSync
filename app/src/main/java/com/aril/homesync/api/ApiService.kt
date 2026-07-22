package com.aril.homesync.api

import com.aril.homesync.data.model.device.DeviceResponse
import com.aril.homesync.data.model.device.UpdateDeviceRequest
import com.aril.homesync.data.model.device.UpdateDeviceResponse
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordRequest
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordResponse
import com.aril.homesync.data.model.login.LoginRequest
import com.aril.homesync.data.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("auth/forgot-password")
    fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Call<ForgotPasswordResponse>

    @GET("devices")
    fun getDevices(): Call<DeviceResponse>

    @PATCH("devices/{id}")
    fun updateDevice(
        @Path("id") id: Int,
        @Body request: UpdateDeviceRequest
    ): Call<UpdateDeviceResponse>
}
