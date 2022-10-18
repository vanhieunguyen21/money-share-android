package com.example.moneyshare.network.request

data class RegisterRequest(
    val username: String,
    val password: String,
    val displayName: String,
)
