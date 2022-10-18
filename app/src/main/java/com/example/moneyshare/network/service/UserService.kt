package com.example.moneyshare.network.service

import com.example.moneyshare.network.dto.UserDTO
import com.example.moneyshare.network.request.LoginRequest
import com.example.moneyshare.network.request.RegisterRequest
import com.example.moneyshare.network.request.UpdateUserRequest
import com.example.moneyshare.network.response.LoginResponse
import com.example.moneyshare.network.response.RefreshTokenResponse
import com.example.moneyshare.network.response.SimpleResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("checkUsername/{username}")
    suspend fun checkUsernameAvailability(@Path("username") username: String): Response<SimpleResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<SimpleResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("refresh")
    suspend fun refreshAccessToken(@Body refreshToken: String): Response<RefreshTokenResponse>

    @PUT("auth/{userID}")
    suspend fun updateUser(
        @Header("Authorization") accessToken: String,
        @Path("userID") userID: Long,
        @Body updateUserRequest: UpdateUserRequest
    ): Response<UserDTO>

    @Multipart
    @POST("auth/{userID}/profileImage")
    suspend fun uploadProfileImage(
        @Header("Authorization") accessToken: String,
        @Path("userID") userID: Long,
        @Part file: MultipartBody.Part
    ) : Response<UserDTO>
}