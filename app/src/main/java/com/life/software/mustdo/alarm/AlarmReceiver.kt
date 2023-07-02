package com.life.software.mustdo.alarm

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.life.software.mustdo.R
import com.life.software.mustdo.di.DaggerApplicationComponent
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.AlarmOffUseCase
import com.life.software.mustdo.domain.useCase.DoneTasksUseCase
import com.life.software.mustdo.domain.useCase.GetTaskUseCase
import com.life.software.mustdo.presentation.MainActivity
import com.life.software.mustdo.presentation.TaskApp
import com.life.software.mustdo.utils.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var  getTaskUseCase: GetTaskUseCase
    @Inject
    lateinit var  doneTasksUseCase: DoneTasksUseCase
    @Inject
    lateinit var alarmOff: AlarmOffUseCase

    /**
     * sends notification when receives alarm
     * */
    override fun onReceive(context: Context, intent: Intent) {
        (context.applicationContext as TaskApp).component.inject(this)
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val taskId = intent.getIntExtra("taskId", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val task = getTaskUseCase(taskId)
            alarmOff(taskId)
            Log.d("DEBUG_RECIVER","Task from receiver: $task")
            notificationManager.sendReminderNotification(
                applicationContext = context,
                channelId = context.getString(R.string.reminders_notification_channel_id),
                task
            )
            // Remove this line if you don't want to reschedule the reminder
            //RemindersManager.startReminder(context.applicationContext, task)
        }
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String, task: Task?
) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    contentIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle(applicationContext.getString(R.string.description_notification_reminder))
        .setContentText(task?.taskInfo)
        .setSmallIcon(R.drawable.date_reminder_icon)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(task?.taskInfo)
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

