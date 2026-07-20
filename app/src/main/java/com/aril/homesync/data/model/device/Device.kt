package com.aril.homesync.data.model.device

data class Device(
    val id: Int,
    val name: String,
    val type: String,
    val location: String,
    val status: String,
    val icon: String
)

