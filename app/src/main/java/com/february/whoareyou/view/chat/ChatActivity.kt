package com.february.whoareyou.view.chat

import android.os.Bundle
import com.february.whoareyou.R
import com.february.whoareyou.databinding.ActivityChatBinding
import com.february.whoareyou.view.base.BaseActivity

class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }
}