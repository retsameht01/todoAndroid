package com.tinle.mytodos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.mytodos.data.Todo

class TodoDetailsVM:ViewModel() {
    private var viewData: MutableLiveData<List<Todo>> = MutableLiveData()
    private val executor = AppExecutor()
    private var db = App.getDb()

    fun getData():LiveData<List<Todo>> {
        loadData()
        return viewData
    }

    private fun loadData() {
        executor.diskIO().execute {
            val todos =  db!!.todoDao().getTodos(App.getSelectedUser())
            executor.mainThread().execute {
                viewData.value = todos
            }
        }
    }

    fun getUserId():String{
        return App.getSelectedUser()
    }
}