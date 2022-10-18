package com.example.moneyshare.domain.model

import java.time.Instant

data class User(
    val id: Long = 0L,
    val username: String = "",
    val displayName: String = "",
    val profileImageUrl:String? = null,
    val phoneNumber: String? = null,
    val emailAddress: String? = null,
    val dateOfBirth: Instant? = null,
)
