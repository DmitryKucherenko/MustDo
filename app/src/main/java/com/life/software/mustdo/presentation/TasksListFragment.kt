package com.life.software.mustdo.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.life.software.mustdo.databinding.TasksListFragmentBinding
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.presentation.adapter.TaskAdapter
import javax.inject.Inject

class TasksListFragment : Fragment() {
    private var _binding: TasksListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TasksListViewModel::class.java]
    }

    private var taskRecyclerView: RecyclerView? = null
    private var adapter: TaskAdapter? = null
    private var addTaskButton: FloatingActionButton? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TaskApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TasksListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupListener()
        viewModel.getTaskList().observe(
            viewLifecycleOwner
        ) { taskList ->
            adapter?.submitList(taskList)
        }
    }

    private fun initView() {
        with(binding) {
            taskRecyclerView = recyclerView
            addTaskButton = floatingActionButton2
        }
        taskRecyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = TaskAdapter()
        taskRecyclerView?.adapter = adapter
    }

    private fun setupListener(){
        addTaskButton?.setOnClickListener {
            val task = Task(0,"TETST","20.06.2022")
            viewModel.addTask(task)
        }
    }
}