package com.example.moneyshare.repository

import com.example.moneyshare.domain.data_source.room.AppDatabase
import com.example.moneyshare.domain.data_source.room.entity.GroupEntity
import com.example.moneyshare.domain.data_source.room.entity.PaymentBatchEntity
import com.example.moneyshare.domain.data_source.room.mapper.EntityMapper
import com.example.moneyshare.domain.model.Group
import java.util.*

class GroupRepositoryImpl(
    private val appDatabase: AppDatabase,
) : GroupRepository {
    override suspend fun createGroup(name: String): Group? {
//        var generatedId = 0L
//        appDatabase.runInTransaction {
//            try {
//                // Insert group
//                generatedId = appDatabase.groupDao().insert(GroupEntity(name = name))[0]
//                // Insert new payment batch
//                appDatabase.paymentBatchDao().insert(
//                    PaymentBatchEntity(
//                        startTimestamp = Calendar.getInstance().timeInMillis,
//                        groupId = generatedId
//                    )
//                )
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//                generatedId = 0
//            }
//        }
//        if (generatedId != 0L) {
//            val groupEntity = appDatabase.groupDao().get(generatedId)
//            return if (groupEntity == null) null else EntityMapper.mapToGroup(groupEntity)
//        }
        return null
    }

    override suspend fun getAllGroups(): List<Group> {
        TODO()
//        val groupEntities = groupDao.getAll()
//        return groupEntities.map(EntityMapper::mapToGroup)
    }
}