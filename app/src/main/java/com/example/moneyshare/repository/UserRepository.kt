package com.example.moneyshare.repository

import com.example.moneyshare.domain.model.ResultWithMessage

interface UserRepository {
    suspend fun checkUsernameAvailability(username: String): ResultWithMessage<Boolean>

}