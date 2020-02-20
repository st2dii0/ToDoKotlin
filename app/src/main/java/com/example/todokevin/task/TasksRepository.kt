package com.example.todokevin.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todokevin.network.Api


class TasksRepository {
    private val taskWebService = Api.taskWebService

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    suspend fun refresh() {
        val tasksResponse = taskWebService.getTasks()
        if (tasksResponse.isSuccessful) {
            val fetchedTasks = tasksResponse.body()
            _taskList.postValue(fetchedTasks)
        }
    }

    suspend fun updateTask(task: Task) {
        val tasksResponse = taskWebService.updateTask(task)
        if(tasksResponse.isSuccessful){
            val editableList = _taskList.value.orEmpty().toMutableList()
            val position = editableList.indexOfFirst { task.id == it.id }
            editableList[position] = task
            _taskList.value = editableList
        }
    }
}