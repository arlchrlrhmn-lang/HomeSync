package com.aril.homesync.data.repository

import com.aril.homesync.api.ApiClient
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordRequest
import com.aril.homesync.data.model.forgotpassword.ForgotPasswordResponse
import com.aril.homesync.data.model.login.LoginRequest
import com.aril.homesync.data.model.login.LoginResponse
import retrofit2.Call

class AuthRepository {

    fun login(request: LoginRequest): Call<LoginResponse> {
        return ApiClient.apiservice.login(request)
    }

    fun forgotPassword(request: ForgotPasswordRequest
    ): Call<ForgotPasswordResponse> {
        return ApiClient.apiservice.forgotPassword(request)
    }
}