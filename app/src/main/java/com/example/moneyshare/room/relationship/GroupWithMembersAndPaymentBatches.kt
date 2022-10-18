package com.example.moneyshare.room.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.moneyshare.room.entity.GroupEntity
import com.example.moneyshare.room.entity.MemberEntity
import com.example.moneyshare.room.entity.PaymentBatchEntity

data class GroupWithMembersAndPaymentBatches(
    @Embedded val group: GroupEntity,
    @Relation(
        entity = MemberEntity::class,
        parentColumn = "id",
        entityColumn = "groupId",
    )
    val members: List<MemberEntity>,
    @Relation(
        entity = PaymentBatchEntity::class,
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val paymentBatches: List<PaymentBatchWithPayments>
)