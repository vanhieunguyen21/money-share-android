package com.example.moneyshare.domain.data_source.room.mapper

import com.example.moneyshare.domain.data_source.room.entity.MemberEntity
import com.example.moneyshare.domain.data_source.room.entity.PaymentEntity
import com.example.moneyshare.domain.data_source.room.relationship.GroupWithMembersAndPaymentBatches
import com.example.moneyshare.domain.data_source.room.relationship.PaymentBatchWithPayments
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.domain.model.Member

object EntityMapper {

//    fun mapToMember(entity: MemberEntity): Member {
//        return Member(
//            id = entity.id!!,
//            name = entity.name!!
//        )
//    }
//
//    fun mapToPayment(entity: PaymentEntity): Payment {
//        return Payment(
//            id = entity.id!!,
//            name = entity.name!!,
//            amount = entity.amount!!,
//            paymentTimestamp = entity.paymentTimestamp!!,
//            description = entity.description,
//            imageUrl = entity.imageUrl
//        )
//    }
//
//    fun mapToPaymentBatch(entity: PaymentBatchWithPayments): PaymentBatch {
//        return PaymentBatch(
//            id = entity.paymentBatch.id!!,
//            startTimestamp = entity.paymentBatch.startTimestamp!!,
//            endTimestamp = entity.paymentBatch.endTimestamp,
//            payments = entity.payments.map(::mapToPayment)
//        )
//    }
//
//    fun mapToGroup(entity: GroupWithMembersAndPaymentBatches): Group {
//        return Group(
//            id = entity.group.id!!,
//            name = entity.group.name!!,
//            members = entity.members.map(::mapToMember) ,
//            currentBatchId = entity.group.currentBatchId!!,
//            batches = entity.paymentBatches.map(::mapToPaymentBatch)
//        )
//    }
}