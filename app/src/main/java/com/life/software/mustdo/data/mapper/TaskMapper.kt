package com.life.software.mustdo.data.mapper

import com.life.software.mustdo.data.model.TaskDbModel
import com.life.software.mustdo.domain.model.Task

class TaskMapper {
    companion object {
        fun taskDbModelToTask(taskDbModel: TaskDbModel) =
            Task(
                taskDbModel.id,
                taskDbModel.taskInfo,
                taskDbModel.addDate,
                taskDbModel.alarmDate,
                taskDbModel.alarmActive,
                taskDbModel.done
            )

        fun taskDbListToTaskList(taskDbList: List<TaskDbModel>) =
            taskDbList.map { taskDbModelToTask(it) }

        fun taskListToDbList(taskList:List<Task>) = taskList.map {
            taskToTaskDbModel(it)
        }

        fun taskToTaskDbModel(task: Task): TaskDbModel {
            return TaskDbModel(
                task.id,
                task.taskInfo,
                task.addDate,
                task.alarmDate,
                task.alarmActive,
                task.done
            )

        }
    }
}