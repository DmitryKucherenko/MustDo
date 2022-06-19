package com.life.software.mustdo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.life.software.mustdo.databinding.TaskItemBinding
import com.life.software.mustdo.domain.model.Task

class TaskAdapter : ListAdapter<Task, TaskHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
       val task = currentList[position]
        holder.bind(task)
    }

    override fun getItemCount() = currentList.size
}