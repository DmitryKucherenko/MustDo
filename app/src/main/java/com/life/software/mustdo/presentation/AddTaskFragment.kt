package com.life.software.mustdo.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import com.life.software.mustdo.databinding.TasksListFragmentBinding

class AddTaskFragment:Fragment() {
    private var _binding: TasksListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val component by lazy {
        (requireActivity().application as TaskApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
}