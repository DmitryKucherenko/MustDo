package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import javax.inject.Inject

class AlarmOffUseCase @Inject constructor(
    private val repository: TasksRepository
) {
     operator fun invoke(taskId: Int) = repository.alarmOff(taskId)
}