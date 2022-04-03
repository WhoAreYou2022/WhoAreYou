package com.february.whoareyou.data.entity

data class MessageModel(
    val owner: Owner,
    val nickName: String,
    val content: String
) {
    var collapseName: Boolean = false

    enum class Owner(val viewType: Int) {
        SENDER(0),
        RECEIVER(1);
    }
}