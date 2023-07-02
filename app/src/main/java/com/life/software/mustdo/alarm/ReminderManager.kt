package com.life.software.mustdo.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.life.software.mustdo.alarm.AlarmReceiver
import com.life.software.mustdo.domain.model.Task

object RemindersManager {
    fun startReminder(
        context: Context,
        task: Task
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Log.d("Reminder","$task.toString()")

        val intent =
            Intent(context.applicationContext, AlarmReceiver::class.java).let { intent ->
                intent.putExtra("taskId", task.id)
                PendingIntent.getBroadcast(
                    context.applicationContext,
                    task.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            task.alarmDate,
            intent
        )
     }

    fun stopReminder(
        context: Context,
        task: Task
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                task.id,
                intent,
                0
            )
        }
        alarmManager.cancel(intent)
    }
}