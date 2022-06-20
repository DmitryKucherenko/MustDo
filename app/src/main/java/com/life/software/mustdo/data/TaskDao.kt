package com.life.software.mustdo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.life.software.mustdo.data.model.TaskDbModel
import com.life.software.mustdo.domain.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskdbmodel")
    fun getTasks(): LiveData<List<TaskDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskDbModel)

    @Query("DELETE FROM taskdbmodel WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("SELECT * FROM taskdbmodel WHERE id=:taskId LIMIT 1")
    suspend fun getTask(taskId: Int): Task
}