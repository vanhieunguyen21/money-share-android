package com.example.moneyshare.network.util

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

object JWTUtil {
    data class Claims(
        @SerializedName("username")
        val username: String,
        @SerializedName("exp")
        val exp: Long,
    )

    fun getClaims(jwt: String): Claims {
        val split = jwt.split("\\.")
        if (split.size == 3) {
            val claimsJson = String(Base64.getDecoder().decode(split[1]))
            return Gson().fromJson(claimsJson, Claims::class.java)
        } else throw Exception("Invalid JWT Token")
    }
}