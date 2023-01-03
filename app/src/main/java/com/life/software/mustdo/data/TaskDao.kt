package com.life.software.mustdo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.life.software.mustdo.data.model.TaskDbModel
import com.life.software.mustdo.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskdbmodel")
    fun getTasks(): Flow<List<TaskDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskDbModel)

    @Query("DELETE FROM taskdbmodel WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM taskdbmodel WHERE id in (:taskList)")
    suspend fun deleteTasks(taskList:List<Int>)

    @Query("SELECT * FROM taskdbmodel WHERE id=:taskId LIMIT 1")
     fun getTask(taskId: Int): Task


     @Query("UPDATE taskdbmodel SET done = 1 WHERE id in (:taskList)")
     suspend fun update(taskList:List<Int>)
}