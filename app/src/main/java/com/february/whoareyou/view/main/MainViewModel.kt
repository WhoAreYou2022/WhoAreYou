package com.february.whoareyou.view.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.february.whoareyou.data.entity.ApiResponse
import com.february.whoareyou.data.entity.SigninResponse
import com.february.whoareyou.data.network.api.RandomChatApi
import com.february.whoareyou.data.network.auth.Auth
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.lang.ref.WeakReference

class MainViewModel(app: Application) : AndroidViewModel(app) {

    var navigatorRef: WeakReference<MainNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    var nickName = MutableLiveData("")

    fun signIn() = viewModelScope.launch {
        nickName.value?.let {
            runCatching {
                validateNickName(it)
                RandomChatApi.signin(it)
            }.onSuccess { response ->
                handleSignin(response)
            }.onFailure { e ->
                Log.e("MainViewModel", "sign-in failure", e)
                toast(e.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }

    private fun validateNickName(nickName: String) {
        require(nickName.trim().isNotEmpty()) {
            "닉네임 형식이 잘못되었습니다."
        }
    }

    private fun handleSignin(response: ApiResponse<SigninResponse>) {
        if (response.success && response.data != null) {
            val signin = response.data

            Auth.signIn(signin.token,
                    signin.refreshToken,
                    signin.nickName)

            navigator?.startRandomChatActivity(response)
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}