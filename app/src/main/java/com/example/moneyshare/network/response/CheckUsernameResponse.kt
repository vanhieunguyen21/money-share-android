package com.example.moneyshare.network.response

import com.google.gson.annotations.SerializedName

data class CheckUsernameResponse(
    @SerializedName("username")
    val username: String,
    @SerializedName("requirement")
    val requirement: Boolean,
    @SerializedName("available")
    val available: Boolean,
    @SerializedName("message")
    val message: String
)
