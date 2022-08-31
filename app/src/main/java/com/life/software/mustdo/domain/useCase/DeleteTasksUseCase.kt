package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import javax.inject.Inject

class DeleteTasksUseCase  @Inject constructor(
    private val repository: TasksRepository
) {
    suspend operator fun invoke(listId:List<Int>) = repository.deleteTasks(listId)
}