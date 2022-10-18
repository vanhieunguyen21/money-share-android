package com.example.moneyshare.network.request

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("emailAddress")
    val emailAddress: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,
)