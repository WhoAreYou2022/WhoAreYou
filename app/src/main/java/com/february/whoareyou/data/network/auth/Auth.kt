package com.february.whoareyou.data.network.auth

import com.february.whoareyou.Prefs

object Auth {
    fun signIn(token: String, refreshToken: String, nickName: String) {
        Prefs.token = token
        Prefs.refreshToken = refreshToken
        Prefs.nickname = nickName
    }

    fun signOut() {
        Prefs.token = null
        Prefs.refreshToken = null
        Prefs.nickname = null
    }

    fun refreshToken(token: String) {
        Prefs.token = token
    }
}