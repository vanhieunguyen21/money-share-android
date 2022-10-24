package com.example.moneyshare.network.service

import com.example.moneyshare.network.dto.GroupDTO
import com.example.moneyshare.network.request.GroupCreationRequest
import retrofit2.Response
import retrofit2.http.*

interface GroupService {
    @POST("group")
    suspend fun createGroup(
        @Header("Authorization") accessToken: String,
        @Body request: GroupCreationRequest,
    ): Response<GroupDTO>

    @GET("group/user/{userID}")
    suspend fun getGroupByUser(
        @Header("Authorization") accessToken: String,
        @Path("userID") userID: Long,
    ): Response<List<GroupDTO>>

    @GET("group/{groupID}")
    suspend fun getGroupByID(
        @Header("Authorization") accessToken: String,
        @Path("groupID") groupID: Long,
    ): Response<GroupDTO>
}