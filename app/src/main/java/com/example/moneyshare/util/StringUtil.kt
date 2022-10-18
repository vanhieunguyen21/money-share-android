package com.example.moneyshare.util

fun String.limitLength(maxLength: Int, ellipsis: Boolean = false): String {
    return if (length <= maxLength) this
    else if (ellipsis) this.substring(0, maxLength - 3) + "..."
    else this.substring(0, maxLength)
}