package com.life.software.mustdo.utils

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.life.software.mustdo.alarm.AlarmReceiver
import com.life.software.mustdo.di.ApplicationScope
import com.life.software.mustdo.domain.model.Task
import javax.inject.Inject

@ApplicationScope
class RemindersManager @Inject constructor(
    val application: Application
) {
    private val alarmManager =
        application.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun startReminder(
        context: Context,
        task: Task
    ) {
        val intent =
            Intent(context, AlarmReceiver::class.java).let { intent ->
                intent.putExtra("taskId", task.id)
                PendingIntent.getBroadcast(
                    context,
                    task.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            task.alarmDate,
            intent
        )
    }

    fun stopReminder(
        context: Context,
        task: Task
    ) {
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                task.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}