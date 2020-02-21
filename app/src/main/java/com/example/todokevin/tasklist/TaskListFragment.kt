package com.example.todokevin.tasklist

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todokevin.R
import com.example.todokevin.network.Api
import com.example.todokevin.task.Task
import com.example.todokevin.task.TaskActivity
import com.example.todokevin.task.TaskActivity.Companion.ADD_TASK_REPLY_CODE
import com.example.todokevin.task.TasksRepository
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class TaskListFragment : Fragment() {

    lateinit var taskListAdapter: TaskListAdapter
    private val tasksRepository = TasksRepository()
    private val tasks = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksRepository.taskList.observe(this, Observer {
            tasks.clear()
            tasks.addAll(it)
            taskListAdapter.notifyDataSetChanged()
        })
        taskListAdapter = TaskListAdapter(tasks)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = taskListAdapter
        create_task.setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
        taskListAdapter.onDeleteClickListener = { task ->
            tasks.remove(task)
            taskListAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            text_view.text = "${userInfo.firstName} ${userInfo.lastName}"
            tasksRepository.refresh()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var reply = data!!.getSerializableExtra(TaskActivity.TASK_EXTRA_KEY) as Task
                lifecycleScope.launch {
                    tasksRepository.createTask(reply)
                }
            }
        } else if (requestCode == UPDATE_TASK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var reply = data!!.getSerializableExtra(TaskActivity.TASK_EXTRA_KEY) as Task
                lifecycleScope.launch {
                    tasksRepository.updateTask(reply)
                }
            }
        }
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val UPDATE_TASK_REQUEST_CODE = 777
    }
}
