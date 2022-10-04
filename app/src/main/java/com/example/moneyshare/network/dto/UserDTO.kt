package com.example.moneyshare.network.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("emailAddress")
    val emailAddress: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,
)
