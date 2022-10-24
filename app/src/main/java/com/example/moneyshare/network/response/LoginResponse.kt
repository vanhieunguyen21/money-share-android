package com.example.moneyshare.network.response

import com.example.moneyshare.domain.model.User
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

data class LoginResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("emailAddress")
    val emailAddress: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: Long? = null,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
) {
    fun getUser(): User {
        return User(
            id = id ?: 0L,
            username = username.orEmpty(),
            displayName = displayName.orEmpty(),
            profileImageUrl = profileImageUrl,
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            dateOfBirth = dateOfBirth?.let { Instant.ofEpochSecond(it) }
        )
    }
}
