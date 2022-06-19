package com.life.software.mustdo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "taskdbmodel")
class TaskDbModel (
    @PrimaryKey
    val id: Int,
    val taskInfo:String,
    val date: String
)