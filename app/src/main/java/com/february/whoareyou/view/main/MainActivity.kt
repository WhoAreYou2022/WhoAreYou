package com.february.whoareyou.view.main

import android.os.Bundle
import com.february.whoareyou.view.base.BaseActivity
import com.february.whoareyou.R
import com.february.whoareyou.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}