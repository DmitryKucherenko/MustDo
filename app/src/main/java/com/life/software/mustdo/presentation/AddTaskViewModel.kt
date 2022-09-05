package com.life.software.mustdo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.AddTaskUseCase
import com.life.software.mustdo.domain.useCase.DeleteTaskUseCase
import com.life.software.mustdo.domain.useCase.GetTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase
): ViewModel() {
    private var _finish = MutableLiveData<Boolean>(false)
    val finish: LiveData<Boolean>
        get() = _finish

    private var _task = MutableLiveData(Task())
    val task: LiveData<Task>
        get() = _task

    fun saveTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(task)
            _finish.postValue(true)
        }
    }

    fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(getTaskUseCase(id))
        }
    }
}