package com.february.whoareyou.view.main

import com.february.whoareyou.data.entity.ApiResponse
import com.february.whoareyou.data.entity.SigninResponse

interface MainNavigator {
    fun startRandomChatActivity(response: ApiResponse<SigninResponse>)
}