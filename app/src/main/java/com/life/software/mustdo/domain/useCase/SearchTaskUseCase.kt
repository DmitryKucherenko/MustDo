package com.life.software.mustdo.domain.useCase

import com.life.software.mustdo.domain.TasksRepository
import com.life.software.mustdo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTaskUseCase @Inject constructor(
    private val repository: TasksRepository
)  {
     operator fun invoke(searchQuery:String): Flow<List<Task>> = repository.searchTasks(searchQuery)
}