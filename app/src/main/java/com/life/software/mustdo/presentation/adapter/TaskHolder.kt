package com.life.software.mustdo.presentation.adapter

import android.graphics.Paint
import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.life.software.mustdo.databinding.TaskItemBinding
import com.life.software.mustdo.domain.model.Task
import java.text.DateFormat

class TaskHolder(
    binding: TaskItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val taskText = binding.taskText
    val checkImage = binding.checkImageView
    private val alarmDateText = binding.alarmDate
    private val alarmImage = binding.imageView

    fun bind(task: Task) {
        with(taskText) {
            text = task.taskInfo
            paintFlags = if (task.done)
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        val alarmVisibility = if (task.alarmActive) View.VISIBLE else View.GONE
        with(alarmDateText) {
            text = DateFormat.getDateTimeInstance().format(task.alarmDate)
            visibility = alarmVisibility
        }
        alarmImage.visibility = alarmVisibility
    }

}