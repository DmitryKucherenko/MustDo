package com.life.software.mustdo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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


     @Query("SELECT * FROM taskdbmodel WHERE id in (:taskList)")
     fun getSelectedTasks(taskList:List<Int>):Flow<List<TaskDbModel>>


    @Query("UPDATE taskdbmodel SET done = (NOT done) WHERE id in(:taskIdList)")
    fun tasksDone(taskIdList:List<Int>)

}