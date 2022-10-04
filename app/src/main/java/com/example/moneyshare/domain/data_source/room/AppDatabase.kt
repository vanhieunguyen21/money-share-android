package com.example.moneyshare.domain.data_source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneyshare.domain.data_source.room.dao.GroupDao
import com.example.moneyshare.domain.data_source.room.dao.MemberDao
import com.example.moneyshare.domain.data_source.room.dao.PaymentBatchDao
import com.example.moneyshare.domain.data_source.room.dao.PaymentDao
import com.example.moneyshare.domain.data_source.room.entity.GroupEntity
import com.example.moneyshare.domain.data_source.room.entity.MemberEntity
import com.example.moneyshare.domain.data_source.room.entity.PaymentBatchEntity
import com.example.moneyshare.domain.data_source.room.entity.PaymentEntity

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