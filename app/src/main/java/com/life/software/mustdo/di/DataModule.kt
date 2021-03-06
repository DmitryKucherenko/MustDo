package com.life.software.mustdo.di

import android.app.Application
import android.content.Context
import com.life.software.mustdo.data.AppDatabase
import com.life.software.mustdo.data.TaskDao
import com.life.software.mustdo.data.TaskRepositoryImpl
import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.domain.model.Task
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
        fun provideTaskDao(applicaiton: Application):TaskDao{
            return AppDatabase.getInstance(applicaiton).taskDao()
        }
    }
}