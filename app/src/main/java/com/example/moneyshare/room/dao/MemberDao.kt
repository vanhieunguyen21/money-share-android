package com.example.moneyshare.room.dao

import androidx.room.*
import com.example.moneyshare.room.entity.MemberEntity

@Dao
interface MemberDao {
    @Insert
    fun insert(members: MemberEntity): Long

    @Update
    fun update(members: MemberEntity): Int

    @Delete
    fun delete(members: MemberEntity): Int
}