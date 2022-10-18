package com.example.moneyshare.repository

import com.example.moneyshare.domain.model.Group

interface GroupRepository {
    suspend fun createGroup(name: String): Result<Group>

    suspend fun getGroupByUser(userID: Long): Result<List<Group>>

    suspend fun getGroupByID(groupID: Long): Result<Group>
}