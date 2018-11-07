package com.tinle.mytodos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.mytodos.api.ApiHandler
import com.tinle.mytodos.data.Todo
import com.tinle.mytodos.data.TodoDao
import com.tinle.mytodos.data.TodoUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainVM:ViewModel() {
    private var viewData:MutableLiveData<List<TodoUser>> = MutableLiveData()
    private val apiHandler:ApiHandler = ApiHandler()
    private val executor = AppExecutor()
    private val db = App.getDb()
    private val userTodoMap:HashMap<String, MutableList<Todo>> = HashMap()
    private var todoDao:TodoDao
    private val users:MutableList<TodoUser> = mutableListOf()
    init {
        todoDao = db!!.todoDao()
    }

    private fun loadData() {
        users.clear()
        executor.networkIO().execute {
            todoDao.clearUsers()
            apiHandler.getTodo(object : Callback<List<Todo>> {
                override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                    print("Failed to download data")
                    executor.mainThread().execute {
                        users.sortByDescending{selector(it)}
                        viewData.value = mutableListOf()
                    }
                }

                override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                    val list = response.body()?.toList() ?: listOf()
                    executor.diskIO().execute {
                        for(todo in list) {
                            addToMap(todo)
                        }
                        for (uid in userTodoMap.keys) {
                            var inCount = 0
                            val todos = userTodoMap.get(uid)
                            if (todos != null) {
                                for (todo in todos) {
                                    if (!todo.completed) {
                                        inCount++
                                    }
                                }
                            }
                            val user = TodoUser(uid, inCount)
                            todoDao.insertUser(user)
                            users.add(user)

                            if (todos != null) {
                                for (todo in todos) {
                                    todoDao.insertTodo(todo)
                                }
                            }
                        }

                        executor.mainThread().execute {
                            users.sortByDescending{selector(it)}
                            viewData.value = users
                        }
                    }
                }
            })
        }

    }

    fun selector(u:TodoUser):Int = u.incompleteCount

    private fun addToMap(todo: Todo) {
        if (userTodoMap.containsKey(todo.userId)) {
           val list =  userTodoMap.get(todo.userId)
           list!!.add(todo)
           userTodoMap.put(todo.userId, list)
        }
        else {
            val list = mutableListOf<Todo>()
            list.add(todo)
            userTodoMap.put(todo.userId, list)
        }
    }

    fun getData():LiveData<List<TodoUser>> {
        loadData()
        return viewData
    }
}