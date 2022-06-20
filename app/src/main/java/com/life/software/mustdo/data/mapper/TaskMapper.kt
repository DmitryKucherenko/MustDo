package com.life.software.mustdo.data.mapper

import com.life.software.mustdo.data.model.TaskDbModel
import com.life.software.mustdo.domain.model.Task

class TaskMapper {
    companion object {
        fun taskDbModelToTask(taskDbModel: TaskDbModel) =
            Task(
                taskDbModel.id,
                taskDbModel.taskInfo,
                taskDbModel.date
            )

        fun taskDbListToTaskList(taskDbList: List<TaskDbModel>) =
            taskDbList.map { taskDbModelToTask(it) }

        fun taskToTaskDbModel(task:Task):TaskDbModel{
            return TaskDbModel(
                task.id,
                task.taskInfo,
                task.date
            )

        }
    }
}