package com.life.software.mustdo.presentation


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.AddTaskUseCase
import com.life.software.mustdo.domain.useCase.GetTaskUseCase
import com.life.software.mustdo.utils.RemindersManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val application: Application,
    private val addTaskUseCase: AddTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val remindersManager: RemindersManager
): ViewModel() {
    private var _finish = MutableLiveData<Boolean>(false)
    val finish: LiveData<Boolean>
        get() = _finish

    private var _task = MutableLiveData(Task())
    val task: LiveData<Task>
        get() = _task


    fun saveTask(task: Task) {
        Log.d("saveTask","saveTask: $task")
        viewModelScope.launch(Dispatchers.IO) {
            val id = addTaskUseCase(task)
            if(task.alarmActive){
                remindersManager.startReminder(application, task.copy(id = id.toInt()))
            }
            _finish.postValue(true)
        }
    }

    fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(getTaskUseCase(id))
        }
    }
}