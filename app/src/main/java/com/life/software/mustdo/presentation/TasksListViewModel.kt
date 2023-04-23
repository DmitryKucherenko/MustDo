package com.life.software.mustdo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    private val getTasksUseCase: GetTaskListUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val doneTasksUseCase: DoneTasksUseCase
) : ViewModel() {

    val getTaskList: Flow<List<Task>> = getTasksUseCase().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        replay = 1
    )


    fun deleteTasks(IdList: List<Int>) {
        viewModelScope.launch {
            deleteTasksUseCase(IdList)
        }
    }

    fun doneTasks(tasksIdList: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            doneTasksUseCase(tasksIdList)
        }
    }

}