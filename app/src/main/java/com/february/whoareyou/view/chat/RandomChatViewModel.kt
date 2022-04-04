package com.february.whoareyou.view.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.february.whoareyou.Prefs
import com.february.whoareyou.clearTasksAndStartNewActivity
import com.february.whoareyou.data.entity.Message
import com.february.whoareyou.data.entity.MessageModel
import com.february.whoareyou.data.network.api.RandomChatApi
import com.february.whoareyou.data.network.auth.Auth
import com.february.whoareyou.view.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.lang.ref.WeakReference

class RandomChatViewModel(app: Application) : AndroidViewModel(app), RandomChatMessageListener {
    private val client = RandomChatWebSocketClient(this)

    var navigatorRef: WeakReference<RandomChatNavigator>? = null

    private val navigator get() = navigatorRef?.get()
    val inputMessage = MutableLiveData("")

    val messages = mutableListOf<MessageModel>()

    private fun handleMessage(
        owner: MessageModel.Owner,
        message: Message
    ) {
        synchronized(messages) {
            val messageModel = MessageModel(
                owner,
                message.senderNickName,
                message.message
            )

            messages.lastOrNull()?.let { lastMessage ->
                messageModel.collapseName =
                    lastMessage.nickName == messageModel.nickName &&
                            lastMessage.owner == messageModel.owner
            }

            messages.add(messageModel)

            viewModelScope.launch(Dispatchers.Main) {
                navigator?.onMessage(messageModel)
            }
        }
    }

    fun sendMessage() = viewModelScope.launch {
        inputMessage.value?.let { content ->
            if(content.isEmpty() || content.isBlank())
                return@launch

            inputMessage.value = ""

            runCatching {
                RandomChatApi.sendMessage(content)

                Prefs.nickname?.let { nickName ->
                    val message = Message(nickName, content)
                    handleMessage(MessageModel.Owner.SENDER, message)
                }
            }.onFailure {
                onMessageError(it)
            }
        }
    }

    override fun onMessage(message: Message) {
        handleMessage(MessageModel.Owner.RECEIVER, message)
    }

    override fun onMessageError(t: Throwable) {
        Log.e(TAG, "메세지 오류 발생", t)
        toast("메세지 오류가 발생했습니다.")
    }

    override fun onNetworkError(t: Throwable) {
        Log.e(TAG, "네트워크 오류 발생", t)
        toast("네트워크 오류가 발생했습니다.")

        Auth.signOut()
        clearTasksAndStartNewActivity<MainActivity>()
    }

    override fun onStart() {
        Log.d(TAG, "채팅 시작")
    }

    override fun onClosed() {
        Auth.signOut()
        clearTasksAndStartNewActivity<MainActivity>()
    }

    companion object {
        const val TAG = "RandomChatViewModel"
    }
}