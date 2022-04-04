package com.february.whoareyou.data.network

import com.february.whoareyou.Prefs
import com.february.whoareyou.clearTasksAndStartNewActivity
import com.february.whoareyou.data.network.auth.Auth
import com.february.whoareyou.view.main.MainActivity
import okhttp3.Interceptor
import okhttp3.Response

class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder().apply {
            Prefs.token?.let { header("Authorization", it) }
            method(original.method(), original.body())
        }.build()

        val response = chain.proceed(request)
        if (response.code() == 401) {
            Auth.signOut()
            clearTasksAndStartNewActivity<MainActivity>()
        }

        return response
    }
}