package com.example.moneyshare.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "payment",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = MemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["memberId"]
        ),
        ForeignKey(
            onDelete = CASCADE,
            entity = PaymentBatchEntity::class,
            parentColumns = ["id"],
            childColumns = ["paymentBatchId"]
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String? = null,
    val amount: Float? = null,
    val paymentTimestamp: Long? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    // Foreign key to member entity
    val memberId: Long? = null,
    // Foreign key to payment batch entity
    val paymentBatchId: Long? = null,
)