package com.example.moneyshare.domain.data_source.room.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.moneyshare.domain.data_source.room.entity.GroupEntity
import com.example.moneyshare.domain.data_source.room.entity.MemberEntity
import com.example.moneyshare.domain.data_source.room.entity.PaymentBatchEntity

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