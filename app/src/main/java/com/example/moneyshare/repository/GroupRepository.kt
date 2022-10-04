package com.example.moneyshare.repository

import com.example.moneyshare.domain.model.Group

interface GroupRepository {
    suspend fun createGroup(name: String): Group?

    suspend fun getAllGroups(): List<Group>
}