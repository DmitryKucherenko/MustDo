package com.life.software.mustdo.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.life.software.mustdo.databinding.AddTaskFragmentBinding
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.utils.getCurrentDateTime
import com.life.software.mustdo.utils.showKeyboard
import javax.inject.Inject

class AddTaskFragment:Fragment() {
    private var _binding: AddTaskFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<AddTaskFragmentArgs>()
    private val taskId by lazy { args.taskId }

    private val component by lazy {
        (requireActivity().application as TaskApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var navController: NavController? = null


    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddTaskViewModel::class.java]
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
        _binding = AddTaskFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        viewModel.finish.observe(viewLifecycleOwner) {
            if (it) navController?.popBackStack()
        }
        with(binding){
            button.setOnClickListener{
                val task = Task(0,editTextTextMultiLine.text.toString(),getCurrentDateTime().toString())
                viewModel.saveTask(task)
            }

            editTextTextMultiLine.showKeyboard()

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}