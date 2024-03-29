package com.life.software.mustdo.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.life.software.mustdo.R
import com.life.software.mustdo.databinding.TasksListFragmentBinding
import com.life.software.mustdo.presentation.adapter.TaskAdapter
import com.life.software.mustdo.utils.UNDEFINED_ID
import com.life.software.mustdo.utils.getDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

class TasksListFragment : Fragment() {
    private var _binding: TasksListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var navController: NavController? = null
    private var searchView: EditText? = null
    private lateinit var mainMenu: Menu


    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TasksListViewModel::class.java]
    }


    private var taskRecyclerView: RecyclerView? = null
    private lateinit var adapter: TaskAdapter
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (adapter.itemSelectedList.isNotEmpty()) {
                    adapter.clearSelectedItem()

                    val currentList =  adapter.currentList.toList()
                    adapter.submitList(currentList)
                } else
                    getDialog(
                        requireContext(),
                        getString(R.string.quit_text),
                        getString(R.string.positive_dialog_button),
                        getString(R.string.negative_button_text)
                    ) {
                        exitProcess(0)
                    }.show()
            }
        })
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getTaskList
                    .collectLatest { taskList ->
                        adapter.submitList(taskList)
                    }
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        mainMenu = menu
        inflater.inflate(R.menu.custom_menu, mainMenu)
        showDeleteMenu(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showDeleteMenu(show: Boolean) {
        for (itemIndex in 0 until mainMenu.size()) {
            if (itemIndex == 1 && adapter.itemSelectedList.size > 1) {
                mainMenu.getItem(itemIndex).isVisible = false
            } else {
                mainMenu.getItem(itemIndex).isVisible = show
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mDelete -> {
                adapter.let {
                    getDialog(
                        requireContext(),
                        getString(R.string.dialog_item_text),
                        getString(R.string.positive_dialog_button),
                        getString(R.string.negative_button_text)
                    ) {
                        viewModel.deleteTasks(it.itemSelectedList)
                        it.clearSelectedItem()
                        showDeleteMenu(false)
                    }.show()

                }
            }
            R.id.edite -> {
                navController?.navigate(
                    TasksListFragmentDirections.actionTasksListFragmentToAddTaskFragment(
                        adapter.itemSelectedList[0]
                    )
                )
            }
            R.id.done -> {
                viewModel.doneTasks(adapter.itemSelectedList)
                adapter.clearSelectedItem()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        with(binding) {
            taskRecyclerView = recyclerView
            addTaskButton = floatingAddButton
            searchView = searchText
        }
        taskRecyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = TaskAdapter { show -> showDeleteMenu(show) }
        taskRecyclerView?.adapter = adapter
        navController = findNavController()

    }

    private fun setupListener() {
        addTaskButton?.setOnClickListener {
            navController?.navigate(
                TasksListFragmentDirections.actionTasksListFragmentToAddTaskFragment(UNDEFINED_ID)
            )
        }
        searchView?.addTextChangedListener {
            viewModel.searchTask(searchView?.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}