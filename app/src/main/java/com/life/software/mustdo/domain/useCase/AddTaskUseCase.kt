package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.domain.model.Task
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TasksRepository
) {
    suspend operator fun invoke(task: Task) = repository.addTask(task)
}