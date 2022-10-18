package com.example.moneyshare.network.response

import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("message")
    val message: String?
)