package com.february.whoareyou.data.network.api

import com.february.whoareyou.data.entity.ApiResponse
import com.february.whoareyou.data.entity.MessageRequest
import com.february.whoareyou.data.entity.SigninResponse
import com.february.whoareyou.data.network.ApiGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RandomChatApi {

    @POST("/api/v1/randomchat/signin")
    suspend fun signin(
        @Query("nickName") nickName: String
    ): ApiResponse<SigninResponse>

    @POST("/api/v1/randomchat/message")
    suspend fun sendMessage(
        @Body request: MessageRequest
    ): ApiResponse<Any>

    companion object {
        private val instance = ApiGenerator()
            .generate(RandomChatApi::class.java)


        suspend fun signin(nickName: String) =
            withContext(Dispatchers.IO) {
                instance.signin(nickName)
            }

        suspend fun sendMessage(message: String) =
            withContext(Dispatchers.IO) {
                instance.sendMessage(MessageRequest(message))
            }
    }
}