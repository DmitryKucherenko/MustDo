package com.life.software.mustdo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.AddTaskUseCase
import com.life.software.mustdo.domain.useCase.DeleteTaskUseCase
import com.life.software.mustdo.domain.useCase.DeleteTasksUseCase
import com.life.software.mustdo.domain.useCase.GetTaskListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskListUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase:DeleteTaskUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,

    ) : ViewModel() {
    fun getTaskList(): Flow<List<Task>> {
        return getTaskUseCase()
    }

    fun addTask(task:Task){
        viewModelScope.launch{
            addTaskUseCase(task)
        }
    }

    fun deleteTask(id:Int){
        viewModelScope.launch {
            deleteTaskUseCase(id)
        }
    }
    fun deleteTasks(IdList:List<Int>){
        viewModelScope.launch {
            deleteTasksUseCase(IdList)
        }
    }
}