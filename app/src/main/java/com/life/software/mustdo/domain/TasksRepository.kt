package com.life.software.mustdo.domain

import androidx.lifecycle.LiveData
import com.life.software.mustdo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun getTask(id:Int):Task
    suspend fun deleteTask(id:Int)
    suspend fun deleteTasks(listId:List<Int>)
    suspend fun doneTasks(listId:List<Int>)
}