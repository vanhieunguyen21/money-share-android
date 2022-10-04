package com.example.moneyshare.domain.data_source.room.dao

import androidx.room.*
import com.example.moneyshare.domain.data_source.room.entity.PaymentBatchEntity

@Dao
interface PaymentBatchDao {
    @Insert
    fun insert(batch: PaymentBatchEntity): Long

    @Update
    fun update(batches: PaymentBatchEntity): Int

    @Delete
    fun delete(batches: PaymentBatchEntity): Int
}