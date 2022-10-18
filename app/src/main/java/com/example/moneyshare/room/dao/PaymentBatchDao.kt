package com.example.moneyshare.room.dao

import androidx.room.*
import com.example.moneyshare.room.entity.PaymentBatchEntity

@Dao
interface PaymentBatchDao {
    @Insert
    fun insert(batch: PaymentBatchEntity): Long

    @Update
    fun update(batches: PaymentBatchEntity): Int

    @Delete
    fun delete(batches: PaymentBatchEntity): Int
}