package com.life.software.mustdo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.AddTaskUseCase
import com.life.software.mustdo.domain.useCase.GetTaskListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskListUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {
    fun getTaskList(): LiveData<List<Task>> {
        return getTaskUseCase()
    }

    fun addTask(task:Task){
        viewModelScope.launch{
            addTaskUseCase(task)
        }

    }
}