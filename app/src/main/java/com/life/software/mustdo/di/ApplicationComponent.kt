package com.life.software.mustdo.di

import android.app.Application
import com.life.software.mustdo.presentation.TasksListFragment
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

     fun inject(tasksListFragment: TasksListFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}