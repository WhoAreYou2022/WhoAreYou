package com.february.whoareyou

import android.app.Activity
import android.content.Intent
import splitties.activities.start

inline fun <reified  A: Activity> clearTasksAndStartNewActivity() {
    App.instance.start<A> {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}