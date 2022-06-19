package com.life.software.mustdo.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.life.software.mustdo.domain.model.Task

object DiffCallback:DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
       return oldItem == newItem
    }

}