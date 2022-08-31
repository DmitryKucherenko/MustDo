package com.life.software.mustdo.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.life.software.mustdo.databinding.TaskItemBinding
import com.life.software.mustdo.domain.model.Task

class TaskHolder(
    binding: TaskItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val taskText = binding.taskText
    val checkImage = binding.checkImageView

    fun bind(task: Task) {
        taskText.text = task.taskInfo
    }

}