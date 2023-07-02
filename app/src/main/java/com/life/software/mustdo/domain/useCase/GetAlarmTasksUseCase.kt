package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import javax.inject.Inject

class GetAlarmTasksUseCase @Inject constructor(
    private val repository: TasksRepository
) {
    operator fun invoke() = repository.getAlarmTasks()
}