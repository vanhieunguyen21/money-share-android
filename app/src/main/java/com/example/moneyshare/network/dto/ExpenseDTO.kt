package com.example.moneyshare.network.dto

import com.google.gson.annotations.SerializedName

data class ExpenseDTO(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("amount")
    val amount: Float? = null,
    @SerializedName("purchaseTime")
    val purchaseTime: String? = null,
    @SerializedName("status")
    val status: String? = null,
)
