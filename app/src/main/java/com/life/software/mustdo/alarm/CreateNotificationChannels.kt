package com.life.software.mustdo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import com.life.software.mustdo.R

fun createNotificationsChannels(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            context.getString(R.string.reminders_notification_channel_id),
            context.getString(R.string.reminders_notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        ContextCompat.getSystemService(context, NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }
}