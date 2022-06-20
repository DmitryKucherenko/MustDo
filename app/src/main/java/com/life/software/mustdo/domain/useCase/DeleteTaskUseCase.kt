package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.domain.model.Task
import javax.inject.Inject

class DeleteTaskUseCase  @Inject constructor(
    private val repository: TasksRepository
) {
    suspend operator fun invoke(id:Int) = repository.deleteTask(id)
}