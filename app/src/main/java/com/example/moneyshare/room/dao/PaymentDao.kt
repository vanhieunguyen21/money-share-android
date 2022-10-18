package com.example.moneyshare.room.dao

import androidx.room.*
import com.example.moneyshare.room.entity.PaymentEntity

@Dao
interface PaymentDao {
    @Insert
    fun insert(payments: PaymentEntity): Long

    @Update
    fun update(payments: PaymentEntity): Int

    @Delete
    fun delete(payments: PaymentEntity): Int
}