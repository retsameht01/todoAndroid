package com.tinle.mytodos.api

import com.tinle.mytodos.data.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ApiHandler {
    private val BASE_URL = "http://jsonplaceholder.typicode.com/"
    private lateinit var retrofit:Retrofit
    private lateinit var api:MyApi
    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api  = retrofit.create(MyApi::class.java)
    }

    fun getTodo(callback: Callback<List<Todo>>) {
        val call = api.getTodo()
        call.enqueue(callback)
    }

}

interface MyApi{
    @GET("todos")
    fun getTodo(): Call<List<Todo>>
}