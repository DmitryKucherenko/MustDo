package com.life.software.mustdo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.life.software.mustdo.domain.useCase.GetAlarmTasksUseCase
import com.life.software.mustdo.presentation.TaskApp
import com.life.software.mustdo.utils.RemindersManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getAlarmTasksUseCase: GetAlarmTasksUseCase

    /*
    * restart reminders alarms when user's device reboots
    * */
    override fun onReceive(context: Context, intent: Intent) {

        (context.applicationContext as TaskApp).component.inject(this)
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            CoroutineScope(Dispatchers.Main).launch {
                getAlarmTasksUseCase().collect { taskList ->
                    for (task in taskList) {
                        RemindersManager.startReminder(context, task)
                    }
                }
            }


        }
    }
}