package com.life.software.mustdo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.GetTasksUseCase
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    private val getTaskUseCase: GetTasksUseCase
) : ViewModel() {
    fun getTaskList(): LiveData<List<Task>> {
        return getTaskUseCase()
    }
}