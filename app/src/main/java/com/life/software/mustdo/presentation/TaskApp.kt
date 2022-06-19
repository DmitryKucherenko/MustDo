package com.life.software.mustdo.presentation

import android.app.Application
import com.life.software.mustdo.di.DaggerApplicationComponent

class TaskApp :Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}