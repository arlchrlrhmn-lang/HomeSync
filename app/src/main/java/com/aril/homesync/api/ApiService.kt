package com.aril.homesync.api

import com.aril.homesync.data.model.device.DeviceResponse
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordRequest
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordResponse
import com.aril.homesync.data.model.login.LoginRequest
import com.aril.homesync.data.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
}