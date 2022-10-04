package com.example.moneyshare.domain.data_source.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "payment_batch",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"]
        )
    ]
)
data class PaymentBatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val startTimestamp: Long? = null,
    val endTimestamp: Long? = null,
    // Foreign key to group entity
    val groupId: Long? = null
)
