package com.life.software.mustdo.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.life.software.mustdo.databinding.TaskItemBinding
import com.life.software.mustdo.domain.model.Task

class TaskAdapter(
    private val showMenuDelete:(Boolean)->Unit
    ) :
    ListAdapter<Task, TaskHolder>(DiffCallback) {
    private var isEnable = false

    val itemSelectedList:List<Int>
    get() = _itemSelectedList.toList()

    private val _itemSelectedList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = currentList[position]
        holder.bind(task)
        if(_itemSelectedList.contains(task.id)){
            holder.checkImage.visibility = View.VISIBLE
        }
        else{
            holder.checkImage.visibility = View.GONE
        }

        holder.itemView.setOnLongClickListener {
            selectItem(holder, task)
            true
        }


        holder.itemView.setOnClickListener{
            if(_itemSelectedList.contains(task.id)){
                holder.checkImage.visibility = View.GONE
                _itemSelectedList.remove(task.id)

                if(_itemSelectedList.isEmpty()){
                    showMenuDelete(false)
                    isEnable = false
                }else{showMenuDelete(true)}
            }else if(isEnable){
                selectItem(holder,task)
            }
        }

    }

    private fun selectItem(holder: TaskHolder, task: Task) {
        isEnable = true
        _itemSelectedList.add(task.id)
        holder.checkImage.visibility =View.VISIBLE
        showMenuDelete(true)
    }

    override fun getItemCount() = currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelectedItem(){
        _itemSelectedList.clear()
        showMenuDelete(false)
        isEnable = false
        notifyDataSetChanged()
    }

}