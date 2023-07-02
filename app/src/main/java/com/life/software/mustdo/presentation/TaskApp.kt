package com.life.software.mustdo.presentation

import android.app.Application
import com.life.software.mustdo.di.DaggerApplicationComponent
import com.life.software.mustdo.utils.createNotificationsChannels

class TaskApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationsChannels(this)

    }
}