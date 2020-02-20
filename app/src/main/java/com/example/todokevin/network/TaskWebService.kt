package com.example.todokevin.network

import com.example.todokevin.task.Task
import retrofit2.Response
import retrofit2.http.*

interface TaskWebService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>
    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String?): Response<String>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}")
    suspend fun updateTask(@Body task: Task, @Path("id") id: String? = task.id): Response<Task>
}