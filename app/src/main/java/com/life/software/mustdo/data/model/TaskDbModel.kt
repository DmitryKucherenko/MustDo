package com.life.software.mustdo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskdbmodel")
data class TaskDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskInfo: String,
    val addDate: Long = System.currentTimeMillis(),
    val alarmDate: Long = 0,
    val alarmActive:Boolean = false,
    val done: Boolean=false
)