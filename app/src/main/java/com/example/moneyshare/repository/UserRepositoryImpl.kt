package com.example.moneyshare.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.User
import com.example.moneyshare.network.dto.UserDTO
import com.example.moneyshare.network.request.LoginRequest
import com.example.moneyshare.network.request.RegisterRequest
import com.example.moneyshare.network.request.UpdateUserRequest
import com.example.moneyshare.network.response.ErrorResponse
import com.example.moneyshare.network.response.LoginResponse
import com.example.moneyshare.network.response.RefreshTokenResponse
import com.example.moneyshare.network.response.SimpleResponse
import com.example.moneyshare.network.service.UserService
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

    override suspend fun checkUsernameAvailability(username: String): Result<Boolean> {
        val response: Response<SimpleResponse>
        try {
            response = userService.checkUsernameAvailability(username)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.result)
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        displayName: String
    ): Result<Boolean> {
        val request = RegisterRequest(username, password, displayName)
        val response: Response<SimpleResponse>

        try {
            response = userService.register(request)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.result)
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

    override suspend fun login(username: String, password: String): Result<LoginResponse> {
        val request = LoginRequest(username, password)
        val response: Response<LoginResponse>

        try {
            response = userService.login(request)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body)
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody?.message))
        }
    }

    override suspend fun getLoggedInUser(authToken: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccessToken(refreshToken: String): Result<String> {
        val response: Response<RefreshTokenResponse>
        try {
            response = userService.refreshAccessToken(refreshToken)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.accessToken)
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

    override suspend fun updateUser(
        userID: Long,
        accessToken: String,
        displayName: String?,
        password: String?,
        phoneNumber: String?,
        emailAddress: String?,
        dateOfBirth: Instant?
    ): Result<User> {
        val request = UpdateUserRequest(
            displayName = displayName,
            password = password,
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            dateOfBirth = dateOfBirth?.epochSecond,
        )
        val response: Response<UserDTO>
        try {
            response = userService.updateUser(accessToken, userID, request)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.toUser())
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

    override suspend fun uploadUserProfileImage(
        userID: Long,
        accessToken: String,
        inputStream: InputStream,
        fileName: String?,
    ): Result<User> {
        val fileBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), inputStream.readBytes())
        val filePart = MultipartBody.Part.createFormData("file", fileName, fileBody)
        val response: Response<UserDTO>
        try {
            response = userService.uploadProfileImage(accessToken, userID, filePart)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.toUser())
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }
}