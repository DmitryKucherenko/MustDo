package com.life.software.mustdo.domain

import androidx.lifecycle.LiveData
import com.life.software.mustdo.domain.model.Task

interface TasksRepository {
    fun getTasks():LiveData<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun getTask(id:Int):Task
    suspend fun deleteTask(id:Int)
}