package com.example.moneyshare.network.service

import com.example.moneyshare.network.dto.UserDTO
import com.example.moneyshare.network.response.CheckUsernameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("checkUsername/{username}")
    suspend fun checkUsernameAvailability(@Path("username") username: String): Response<CheckUsernameResponse>

    @POST("register")
    suspend fun register(@Body userData: UserDTO): Response<UserDTO>
}