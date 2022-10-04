package com.example.moneyshare.domain.data_source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "group"
)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val currentBatchId: Long = 0,
)