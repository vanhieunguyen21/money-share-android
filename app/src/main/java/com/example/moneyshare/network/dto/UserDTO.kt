package com.example.moneyshare.network.dto

import com.example.moneyshare.domain.model.User
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.Instant
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
    val dateOfBirth: String? = null,
) {
    fun toUser(): User {
        var dob: Instant? = null
        // Parse date of birth
        if (dateOfBirth != null) {
            try {
                dob = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .parse(dateOfBirth)?.toInstant()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return User(
            id = id ?: 0L,
            username = username.orEmpty(),
            displayName = displayName.orEmpty(),
            profileImageUrl = profileImageUrl,
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            dateOfBirth = dob
        )
    }
}

fun User.toUserDTO() : UserDTO {
    return UserDTO(
        id = if (id == 0L) null else id,
        username = username,
        displayName = displayName,
        profileImageUrl = profileImageUrl,
        phoneNumber = phoneNumber,
        emailAddress = emailAddress,
        dateOfBirth = dateOfBirth?.let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date.from(it))
        },
    )
}

