package com.february.whoareyou.data.network

import com.february.whoareyou.Prefs
import com.february.whoareyou.clearTasksAndStartNewActivity
import com.february.whoareyou.data.entity.ApiResponse
import com.february.whoareyou.data.network.api.RandomChatRefreshTokenApi
import com.february.whoareyou.data.network.auth.Auth
import com.february.whoareyou.view.main.MainActivity
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401) {
            return runBlocking {
                val tokenResponse = refreshToken()

                handleTokenResponse(tokenResponse)

                Prefs.token?.let { token ->
                    response.request()
                        .newBuilder()
                        .header("Authorization", token)
                        .build()
                }
            }
        }
        return null
    }

    private suspend fun refreshToken() =
        try {
            RandomChatRefreshTokenApi.refreshToken()
        } catch (e: Exception) {
            ApiResponse.error<String>("인증 실패")
        }

    private fun handleTokenResponse(tokenResponse: ApiResponse<String>) {
        if(tokenResponse.success && tokenResponse.data != null) {
            Auth.refreshToken(tokenResponse.data)
        } else {
            Auth.signOut()
            clearTasksAndStartNewActivity<MainActivity>()
        }
    }
}