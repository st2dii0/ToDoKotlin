package com.example.todokevin.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMjUsImV4cCI6MTYxMzU2OTY4M30.w5AEsGVVuL6wbi0X8kIvuG8tv5Hb1vXaV73m9skuNgA"

    private val moshi = Moshi.Builder().build()

    val userService: UserService by lazy { retrofit.create(UserService::class.java) }
    val taskWebService: TaskWebService by lazy { retrofit.create(TaskWebService::class.java) }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}