package com.february.whoareyou.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.february.whoareyou.R
import com.february.whoareyou.data.entity.ApiResponse
import com.february.whoareyou.data.entity.SigninResponse
import com.february.whoareyou.data.network.auth.Auth
import com.february.whoareyou.databinding.ActivityMainBinding
import com.february.whoareyou.view.chat.RandomChatActivity
import splitties.activities.start
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), MainNavigator {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java).also {
            it.navigatorRef = WeakReference(this)
        }
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Auth.signOut()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun startRandomChatActivity(response: ApiResponse<SigninResponse>) {
        start<RandomChatActivity>()
        finish()
    }
}