package com.aril.homesync.utils

import android.content.Context

class SessionManager(context: Context) {

    companion object {
        private const val PREF_NAME = "homesync_pref"

        private const val KEY_TOKEN = "token"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "name"
        private const val KEY_ROLE = "role"
        private const val KEY_PHOTO = "photo"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPreferences.edit()

    fun saveLoginSession(
        token: String,
        email: String,
        name: String,
        role: String,
        photo: String
    ) {
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_ROLE, role)
        editor.putString(KEY_PHOTO, photo)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun getName(): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getRole(): String? {
        return sharedPreferences.getString(KEY_ROLE, null)
    }

    fun getPhoto(): String? {
        return sharedPreferences.getString(KEY_PHOTO, null)
    }


    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}