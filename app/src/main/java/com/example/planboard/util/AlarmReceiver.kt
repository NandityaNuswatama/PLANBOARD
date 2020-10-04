package com.example.planboard.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val myNotification = MyNotification(context)
        val notification = myNotification.getNotificationBuilder().build()
        myNotification.getManager().notify(150, notification)
    }
}