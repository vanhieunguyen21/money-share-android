package com.example.moneyshare.domain.data_source.room.dao

import androidx.room.*
import com.example.moneyshare.domain.data_source.room.entity.MemberEntity
import com.example.moneyshare.domain.data_source.room.relationship.GroupWithMembersAndPaymentBatches

@Dao
interface MemberDao {
    @Insert
    fun insert(members: MemberEntity): Long

    @Update
    fun update(members: MemberEntity): Int

    @Delete
    fun delete(members: MemberEntity): Int
}