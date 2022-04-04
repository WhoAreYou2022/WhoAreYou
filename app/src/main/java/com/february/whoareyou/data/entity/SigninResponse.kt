package com.february.whoareyou.data.entity

data class SigninResponse(
    val token: String,
    val refreshToken: String,
    val nickName: String
)