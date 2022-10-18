package com.example.moneyshare.network.dto

import com.example.moneyshare.domain.model.Group
import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("totalExpense")
    val totalExpense: Float? = null,
    @SerializedName("averageExpense")
    val averageExpense: Float? = null,
    @SerializedName("members")
    val members: List<MemberDTO>? = null,
    @SerializedName("expenses")
    val expenses: List<ExpenseDTO>? = null,
) {
    fun toGroup(): Group {
        return Group(
            id = id ?: 0L,
            name = name.orEmpty(),
            totalExpense = totalExpense ?: 0f,
            averageExpense = averageExpense ?: 0f,
            members = members?.map { it.toMember() } ?: listOf(),
            expenses = expenses?.map { it.toExpense() } ?: listOf(),
        )
    }
}
