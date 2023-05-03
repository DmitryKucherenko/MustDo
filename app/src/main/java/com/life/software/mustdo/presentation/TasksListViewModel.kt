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
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val doneTasksUseCase: DoneTasksUseCase,
    private val searchTaskUseCase: SearchTaskUseCase
) : ViewModel() {

    private val currentTaskList = mutableListOf<Task>()

    private val _searchText = MutableStateFlow<String>("%%")
    val searchText = _searchText.asStateFlow().debounce(600)

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

    fun searchTask(text: String) {
        _searchText.value = "%$text%"
    }



    val getTaskList = searchText.flatMapLatest {
        searchTaskUseCase(it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = currentTaskList
        )

}