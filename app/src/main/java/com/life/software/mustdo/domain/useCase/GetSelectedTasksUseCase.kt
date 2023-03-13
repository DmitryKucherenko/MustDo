package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import javax.inject.Inject

class GetSelectedTasksUseCase @Inject constructor(
    private val repository: TasksRepository
) {
    suspend operator fun invoke(selectedTaskId:List<Int>) = repository.getSelectedTasks(selectedTaskId)
}