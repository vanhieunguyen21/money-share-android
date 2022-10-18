package com.example.moneyshare.room.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.moneyshare.room.entity.MemberEntity
import com.example.moneyshare.room.entity.PaymentEntity

data class MemberWithPayments(
    @Embedded val member: MemberEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "memberId"
    )
    val payments: List<PaymentEntity>
)
