package com.example.moneyshare.domain.data_source.room.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.moneyshare.domain.data_source.room.entity.PaymentBatchEntity
import com.example.moneyshare.domain.data_source.room.entity.PaymentEntity

data class PaymentBatchWithPayments(
    @Embedded val paymentBatch: PaymentBatchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "paymentBatchId"
    )
    val payments: List<PaymentEntity>
)