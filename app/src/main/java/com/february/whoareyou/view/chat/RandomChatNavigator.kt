package com.february.whoareyou.view.chat

import com.february.whoareyou.data.entity.MessageModel

interface RandomChatNavigator {
    fun onMessage(messageModel: MessageModel)
}