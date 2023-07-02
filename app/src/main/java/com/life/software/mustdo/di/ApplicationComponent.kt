package com.life.software.mustdo.di

import android.app.Application
import com.life.software.mustdo.alarm.AlarmReceiver
import com.life.software.mustdo.presentation.AddTaskFragment
import com.life.software.mustdo.presentation.TasksListFragment
import com.life.software.mustdo.alarm.BootReceiver
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(addTaskFragment: AddTaskFragment)
    fun inject(tasksListFragment: TasksListFragment)
    fun inject(alarmReceiver: AlarmReceiver)
    fun inject(bootReceiver: BootReceiver)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}