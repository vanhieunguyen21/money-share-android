package com.example.moneyshare.repository

import android.util.Log
import com.example.moneyshare.domain.model.ResultWithMessage
import com.example.moneyshare.network.response.CheckUsernameResponse
import com.example.moneyshare.network.service.UserService
import retrofit2.Response

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

    override suspend fun checkUsernameAvailability(username: String): ResultWithMessage<Boolean> {
        val response: Response<CheckUsernameResponse>
        try {
            response = userService.checkUsernameAvailability(username)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResultWithMessage(false, e.message)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (body.requirement && body.available) {
                    ResultWithMessage(true, null)
                } else {
                    ResultWithMessage(false, body.message)
                }
            } else {
                ResultWithMessage(false, "Return body is null")
            }
        } else {
            ResultWithMessage(false, "Failed to check username availability")
        }
    }
}