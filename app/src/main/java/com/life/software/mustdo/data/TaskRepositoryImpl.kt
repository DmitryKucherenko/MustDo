package com.life.software.mustdo.data

import android.content.SharedPreferences
import com.life.software.mustdo.data.mapper.TaskMapper
import com.life.software.mustdo.data.model.SettingsModel
import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.utils.APP_PREFERENCE_FIRST_RUN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val settings: SharedPreferences
) : TasksRepository {

    override fun getTasks(): Flow<List<Task>> {
        return (taskDao.getTasks()).map { taskDb ->
            taskDb.map {
                TaskMapper.taskDbModelToTask(it)
            }
        }
    }

    override suspend fun addTask(task: Task): Long {
        return taskDao.addTask(TaskMapper.taskToTaskDbModel(task))
    }

    override suspend fun getTask(id: Int): Task {
        return taskDao.getTask(id)
    }


    override suspend fun deleteTasks(listId: List<Int>) {
        taskDao.deleteTasks(listId)
    }


    override suspend fun doneTasks(tasksIdList: List<Int>) {
        taskDao.tasksDone(tasksIdList)
    }

    override fun searchTasks(searchQuery: String): Flow<List<Task>> {
        return taskDao.search(searchQuery).map { taskDb ->
            taskDb.map {
                TaskMapper.taskDbModelToTask(it)
            }
        }
    }

    override fun getAlarmTasks(): Flow<List<Task>> {
        return taskDao.getAlarmTasks().map { taskDb ->
            taskDb.map {
                TaskMapper.taskDbModelToTask(it)
            }
        }
    }

    override fun alarmOff(taskId: Int) {
        return taskDao.alarmOff(taskId)
    }

    override fun readSettings(): SettingsModel {
        return SettingsModel(settings.getBoolean(APP_PREFERENCE_FIRST_RUN, true))
    }


    override fun writeSettings(settingsModel: SettingsModel) {
        val editor = settings.edit()
        editor.putBoolean(APP_PREFERENCE_FIRST_RUN,false)
        editor.apply()
    }


}

