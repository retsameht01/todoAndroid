package com.tinle.mytodos

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tinle.mytodos.data.Todo
import kotlinx.android.synthetic.main.activity_todo_details.*

class TodoDetailsActivity:AppCompatActivity() {
    private lateinit var viewModel:TodoDetailsVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TodoDetailsVM()
        setContentView(R.layout.activity_todo_details)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        todoList.layoutManager = layoutManager
        topLabel.text = "UserId " + viewModel.getUserId() + ": Todos"
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData().observe(this, Observer<List<Todo>>{
            todoList.adapter = TodoAdapter(it!!)
        })
    }

    inner class TodoAdapter(val todos:List<Todo>): RecyclerView.Adapter<TodoViewholder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TodoViewholder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.todo_row, null)
            return TodoViewholder(view)
        }

        override fun getItemCount(): Int {
            return todos.size
        }

        override fun onBindViewHolder(holder: TodoViewholder, pos: Int) {
            val todo = todos[pos]
            holder.todoId.text = todo.id
            holder.details.text = "${todo.title}"
        }
    }

    inner class TodoViewholder(viewItem: View): RecyclerView.ViewHolder(viewItem){
        var todoId: TextView = viewItem.findViewById(R.id.todoId)
        var details: TextView = viewItem.findViewById(R.id.inCount)
    }

}