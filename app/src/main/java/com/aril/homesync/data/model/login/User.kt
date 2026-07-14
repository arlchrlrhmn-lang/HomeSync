package com.aril.homesync.data.model.login

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String,
    val token: String,
    val role: String,
)
