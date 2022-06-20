package com.life.software.mustdo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.life.software.mustdo.data.mapper.TaskMapper
import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.domain.model.Task
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TasksRepository {
    override fun getTasks(): LiveData<List<Task>> {
        return Transformations.map(taskDao.getTasks()) { taskDb ->
            taskDb.map {
                TaskMapper.taskDbModelToTask(it)
            }
        }
    }

    override suspend fun addTask(task: Task) {
        taskDao.addTask(TaskMapper.taskToTaskDbModel(task))
    }

    override suspend fun getTask(id: Int): Task {
        return taskDao.getTask(id)
    }

    override suspend fun deleteTask(id: Int) {
        taskDao.deleteTask(id)
    }

}