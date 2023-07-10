package com.life.software.mustdo.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.life.software.mustdo.data.AppDatabase
import com.life.software.mustdo.data.TaskDao
import com.life.software.mustdo.data.TaskRepositoryImpl
import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.utils.APP_SETTING
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(repositoryImpl: TaskRepositoryImpl): TasksRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideTaskDao(application: Application):TaskDao{
            return AppDatabase.getInstance(application).taskDao()
        }

        @ApplicationScope
        @Provides
        fun provideSharedPreferences(application: Application):SharedPreferences{
            return  application.applicationContext.getSharedPreferences(APP_SETTING, Context.MODE_PRIVATE)
        }
    }
}