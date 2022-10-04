package com.example.moneyshare.network.dto

import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("groupIdentifier")
    val groupIdentifier: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("totalExpense")
    val totalExpense: Float? = null,
    @SerializedName("averageExpense")
    val averageExpense: Float? = null,
    @SerializedName("members")
    val members: List<MemberDTO>? = null,
    @SerializedName("expenses")
    val expenses: List<ExpenseDTO>?= null,
    )
