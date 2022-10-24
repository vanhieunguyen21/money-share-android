package com.example.moneyshare.constant

import com.example.moneyshare.constant.Constant.GROUP_PROFILE_IMAGE_BASE_URL
import com.example.moneyshare.constant.Constant.USER_PROFILE_IMAGE_BASE_URL

object Constant {
    const val IP = "http://192.168.1.29"
    const val PORT = "8080"
    const val USER_PROFILE_IMAGE_BASE_URL = "$IP:$PORT/user/profileImage/"
    const val GROUP_PROFILE_IMAGE_BASE_URL = "$IP:$PORT/group/profileImage/"
}

fun getUserProfileImageLink(imageName: String): String {
    return "$USER_PROFILE_IMAGE_BASE_URL$imageName"
}

fun getGroupProfileImageLink(imageName: String): String {
    return "$GROUP_PROFILE_IMAGE_BASE_URL$imageName"
}