package com.february.whoareyou

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
        const val HOST = "172.18.102.124"
        const val API_HOST = "http://$HOST"
        const val API_PORT = 8080
        const val WEBSOCKET_ENDPOINT = "ws://$HOST:8080/ws/randomchat"
    }
}