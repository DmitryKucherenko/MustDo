package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.data.model.SettingsModel
import com.life.software.mustdo.domain.TasksRepository
import javax.inject.Inject

class ReadSettingsUseCase @Inject constructor(
    private val repository: TasksRepository
) {
    operator fun invoke(): SettingsModel = repository.readSettings()
}