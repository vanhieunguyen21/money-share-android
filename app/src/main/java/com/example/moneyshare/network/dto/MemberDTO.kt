package com.example.moneyshare.network.dto

import com.google.gson.annotations.SerializedName

data class MemberDTO(
    @SerializedName("user")
    val user: UserDTO? = null,
    @SerializedName("totalExpense")
    val totalExpense: Float? = null,
    @SerializedName("role")
    val role: String? = null,
)
