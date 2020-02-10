package com.example.todokevin.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todokevin.R
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.item_task.*
import java.util.*

class TaskListFragment : Fragment() {

    lateinit var taskListAdapter: TaskListAdapter
    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)

        // return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(recyclerView, savedInstanceState)
        taskListAdapter = TaskListAdapter(taskList)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = taskListAdapter

        floatingActionButton.setOnClickListener {
            // Instanciation d'un objet task avec des données préremplies:
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList.add(newTask)
            taskListAdapter.notifyDataSetChanged()

        }
        taskListAdapter.onDeleteClickListener = { task ->
            taskList.remove(task)
            taskListAdapter.notifyDataSetChanged()
        }
    }
}
