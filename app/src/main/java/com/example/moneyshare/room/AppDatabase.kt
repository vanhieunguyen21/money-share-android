package com.example.moneyshare.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneyshare.room.dao.GroupDao
import com.example.moneyshare.room.dao.MemberDao
import com.example.moneyshare.room.dao.PaymentBatchDao
import com.example.moneyshare.room.dao.PaymentDao
import com.example.moneyshare.room.entity.GroupEntity
import com.example.moneyshare.room.entity.MemberEntity
import com.example.moneyshare.room.entity.PaymentBatchEntity
import com.example.moneyshare.room.entity.PaymentEntity

@Database(
    entities = [
        GroupEntity::class,
        MemberEntity::class,
        PaymentBatchEntity::class,
        PaymentEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun memberDao(): MemberDao
    abstract fun paymentDao(): PaymentDao
    abstract fun paymentBatchDao(): PaymentBatchDao
}