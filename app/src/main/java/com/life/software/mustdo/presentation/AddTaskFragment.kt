package com.life.software.mustdo.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.life.software.mustdo.data.model.SettingsModel
import com.life.software.mustdo.databinding.AddTaskFragmentBinding
import com.life.software.mustdo.domain.model.Task
import com.life.software.mustdo.domain.useCase.ReadSettingsUseCase
import com.life.software.mustdo.domain.useCase.WriteSettingsUseCase
import com.life.software.mustdo.utils.showKeyboard
import java.util.Calendar
import javax.inject.Inject

class AddTaskFragment : Fragment() {
    private var _binding: AddTaskFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val currentDate = Calendar.getInstance()
    val alarmDate = Calendar.getInstance()

    private val args by navArgs<AddTaskFragmentArgs>()
    private val taskId by lazy { args.taskId }

    private val component by lazy {
        (requireActivity().application as TaskApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var readSettingsUseCase: ReadSettingsUseCase

    @Inject
    lateinit var writeSettingsUseCase: WriteSettingsUseCase


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

        with(binding) {
            editTextTextMultiLine.showKeyboard()
            dateView.text = String.format(
                "%02d.%02d.%02d",
                currentDate.get(Calendar.DAY_OF_MONTH),
                Calendar.MONTH,
                Calendar.YEAR
            )
            timeView.text = String.format(
                "%02d:%02d",
                currentDate.get(Calendar.HOUR_OF_DAY),
                currentDate.get(Calendar.MINUTE)
            )
            reminderSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if(readSettingsUseCase().firstRun){
                    writeSettingsUseCase(SettingsModel(firstRun = false))
                    navController?.navigate(
                        AddTaskFragmentDirections.actionAddTaskFragmentToSettingsFragment()
                    )
                }
                val visibility = if (isChecked) {
                    View.VISIBLE
                } else (View.GONE)

                dateView.visibility = visibility
                timeView.visibility = visibility
                timeButton.visibility = visibility
                dateButton.visibility = visibility
            }
        }

        binding.timeButton.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    alarmDate.set(Calendar.HOUR_OF_DAY, hour)
                    alarmDate.set(Calendar.MINUTE, minute)
                    alarmDate.set(Calendar.SECOND,0)
                    binding.timeView.text = String.format("%02d:%02d", hour, minute)
                },
                currentDate.get(Calendar.HOUR_OF_DAY),
                currentDate.get(Calendar.MINUTE),
                true
            ).show()
        }
        binding.dateButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    binding.dateView.text = String.format("%02d.%02d.%02d", dayOfMonth, month, year)
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        if (taskId == -1) {
            launchAddMode()
        } else {
            launchEditMode()
        }

    }


    private fun launchAddMode() {

        binding.saveButton.setOnClickListener {
            Log.d("SWITCH", "${binding.reminderSwitch.isChecked}")
            val task = Task(
                taskInfo = binding.editTextTextMultiLine.text.toString(),
                addDate = System.currentTimeMillis(),
                alarmDate = alarmDate.timeInMillis,
                alarmActive = binding.reminderSwitch.isChecked
            )
            viewModel.saveTask(task)
        }
    }

    private fun launchEditMode() {
        viewModel.getTask(taskId)


        viewModel.task.observe(viewLifecycleOwner, { task ->
            binding.editTextTextMultiLine.setText(task.taskInfo)
            currentDate.timeInMillis = task.alarmDate
            binding.reminderSwitch.isChecked = task.alarmActive
        })



        binding.saveButton.setOnClickListener {
            val taskEdit = viewModel.task.value?.copy(
                taskInfo = binding.editTextTextMultiLine.text.toString(),
                alarmActive = binding.reminderSwitch.isChecked
            )
            taskEdit?.let {
                viewModel.saveTask(taskEdit)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}