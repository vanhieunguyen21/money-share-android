package com.example.moneyshare.repository

import android.net.Uri
import com.example.moneyshare.domain.model.User
import com.example.moneyshare.network.response.LoginResponse
import java.io.InputStream
import java.time.Instant

interface UserRepository {
    suspend fun checkUsernameAvailability(username: String): Result<Boolean>

    suspend fun register(username: String, password: String, displayName: String): Result<Boolean>

    suspend fun login(username: String, password: String): Result<LoginResponse>

    suspend fun getLoggedInUser(authToken: String): Result<User>

    suspend fun refreshAccessToken(refreshToken: String): Result<String>

    suspend fun updateUser(
        userID: Long,
        accessToken: String,
        displayName: String? = null,
        password: String? = null,
        phoneNumber: String? = null,
        emailAddress: String? = null,
        dateOfBirth: Instant? = null
    ) : Result<User>

    suspend fun uploadUserProfileImage(
        userID: Long,
        accessToken: String,
        inputStream: InputStream,
        fileName: String? = null,
    ) : Result<User>
}