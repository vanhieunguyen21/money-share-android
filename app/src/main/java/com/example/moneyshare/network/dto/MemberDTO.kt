package com.example.moneyshare.network.dto

import com.example.moneyshare.domain.model.Member
import com.example.moneyshare.domain.model.Role
import com.example.moneyshare.domain.model.User
import com.google.gson.annotations.SerializedName

data class MemberDTO(
    @SerializedName("user")
    val user: UserDTO? = null,
    @SerializedName("totalExpense")
    val totalExpense: Float? = null,
    @SerializedName("role")
    val role: String? = null,
) {
    fun toMember(): Member {
        return Member(
            user = user?.toUser() ?: User(0, "", ""),
            totalExpense = totalExpense ?: 0f,
            role = when (role) {
                null -> Role.Member
                "member" -> Role.Member
                "manager" -> Role.Manager
                else -> Role.Member
            }
        )
    }
}
