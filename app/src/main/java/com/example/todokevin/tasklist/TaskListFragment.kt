package com.example.todokevin.tasklist

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todokevin.R
import com.example.todokevin.task.TaskActivity
import com.example.todokevin.task.TaskActivity.Companion.ADD_TASK_REPLY_CODE
import kotlinx.android.synthetic.main.fragment_task_list.*


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
        super.onViewCreated(view, savedInstanceState)
        taskListAdapter = TaskListAdapter(taskList)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = taskListAdapter

        create_task.setOnClickListener {
           /* // Instanciation d'un objet task avec des données préremplies:
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList.add(newTask)
            taskListAdapter.notifyDataSetChanged()*/

            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
        taskListAdapter.onDeleteClickListener = { task ->
            taskList.remove(task)
            taskListAdapter.notifyDataSetChanged()
        }
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if(requestCode == ADD_TASK_REQUEST_CODE){
             if(resultCode == RESULT_OK){
                 var reply = data!!.getSerializableExtra(ADD_TASK_REPLY_CODE.toString()) as Task
                 taskList.add(reply)
             }
         }

    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
    }
}
