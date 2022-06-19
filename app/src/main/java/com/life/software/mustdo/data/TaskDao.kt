package com.life.software.mustdo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.life.software.mustdo.data.model.TaskDbModel

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskdbmodel")
    fun getTasks(): LiveData<List<TaskDbModel>>
}