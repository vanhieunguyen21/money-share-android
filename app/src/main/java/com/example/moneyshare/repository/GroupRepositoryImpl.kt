package com.example.moneyshare.repository

import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.network.dto.GroupDTO
import com.example.moneyshare.network.request.GroupCreationRequest
import com.example.moneyshare.network.response.ErrorResponse
import com.example.moneyshare.network.service.GroupService
import com.google.gson.Gson
import retrofit2.Response

class GroupRepositoryImpl(
    private val groupService: GroupService
) : GroupRepository {
    override suspend fun createGroup(name: String): Result<Group> {
        val request = GroupCreationRequest(name)
        val response: Response<GroupDTO>
        try {
            response = groupService.createGroup(Auth.getAccessToken()!!, request)
        } catch (e: Exception) {
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.toGroup())
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

    override suspend fun getGroupByUser(userID: Long): Result<List<Group>> {
        val response: Response<List<GroupDTO>>
        try {
            response = groupService.getGroupByUser(Auth.getAccessToken()!!, userID)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.map { it.toGroup() })
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

    override suspend fun getGroupByID(groupID: Long): Result<Group> {
        val response: Response<GroupDTO>
        try {
            response = groupService.getGroupByID(Auth.getAccessToken()!!, groupID)
        } catch (e: Exception) {
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.toGroup())
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }

}