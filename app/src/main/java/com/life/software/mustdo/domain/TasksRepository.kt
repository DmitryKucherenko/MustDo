package com.life.software.mustdo.domain

import androidx.lifecycle.LiveData
import com.life.software.mustdo.data.model.SettingsModel
import com.life.software.mustdo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task): Long
    suspend fun getTask(id: Int): Task
    suspend fun deleteTasks(listId: List<Int>)
    suspend fun doneTasks(tasksIdList: List<Int>)
    fun searchTasks(searchQuery: String): Flow<List<Task>>
    fun getAlarmTasks(): Flow<List<Task>>
    fun alarmOff(taskId:Int)
    fun readSettings():SettingsModel
    fun writeSettings(settingsModel: SettingsModel)
}