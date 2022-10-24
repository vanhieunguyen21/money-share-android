package com.example.moneyshare.network.dto

import com.example.moneyshare.domain.model.User
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class UserDTO(
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
) {
    fun toUser(): User {
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

fun User.toUserDTO(): UserDTO {
    return UserDTO(
        id = if (id == 0L) null else id,
        username = username,
        displayName = displayName,
        profileImageUrl = profileImageUrl,
        phoneNumber = phoneNumber,
        emailAddress = emailAddress,
        dateOfBirth = dateOfBirth?.epochSecond,
    )
}

