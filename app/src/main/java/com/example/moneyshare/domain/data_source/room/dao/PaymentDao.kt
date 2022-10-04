package com.example.moneyshare.domain.data_source.room.dao

import androidx.room.*
import com.example.moneyshare.domain.data_source.room.entity.PaymentEntity

@Dao
interface PaymentDao {
    @Insert
    fun insert(payments: PaymentEntity): Long

    @Update
    fun update(payments: PaymentEntity): Int

    @Delete
    fun delete(payments: PaymentEntity): Int
}