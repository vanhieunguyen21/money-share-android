package com.example.moneyshare.domain.data_source.room.dao

import androidx.room.*
import com.example.moneyshare.domain.data_source.room.entity.GroupEntity
import com.example.moneyshare.domain.data_source.room.relationship.GroupWithMembersAndPaymentBatches

@Dao
interface GroupDao {
    @Insert
    fun insert(vararg groups: GroupEntity): List<Long>

    @Update
    fun update(groups: GroupEntity): Int

    @Delete
    fun delete(groups: GroupEntity): Int

    @Transaction
    @Query("SELECT * FROM `group` WHERE id = :id")
    fun get(id: Long) : GroupWithMembersAndPaymentBatches?

    @Transaction
    @Query("SELECT * FROM `group`")
    fun getAll(): List<GroupWithMembersAndPaymentBatches>
}