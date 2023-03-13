package com.life.software.mustdo.presentation

import android.util.Log
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
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase:DeleteTaskUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val getSelectedTasksUseCase: GetSelectedTasksUseCase,
    private val doneTasksUseCase: DoneTasksUseCase


    ) : ViewModel() {

    fun getTaskList(): Flow<List<Task>> {
        return getTasksUseCase()
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


    fun doneTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = getTaskUseCase(id)
            val doneTask =  task.copy(done=!task.done)
            addTask(doneTask)
        }
    }

    fun doneTasks(tasksIdList:List<Int>){
        viewModelScope.launch(Dispatchers.IO) {


                doneTasksUseCase(tasksIdList)


        }
    }

}