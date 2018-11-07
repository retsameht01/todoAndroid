package com.tinle.mytodos

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.tinle.mytodos.data.TodoUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /*
    Write a small Android app in kotlin that will display:
    1. An initial screen that consists of a table with one row for each unique "userId"
    2. In each row there should be a count for the number of incomplete todos (determined by "completed": false)
    3. The rows should be sorted descending by the number of incomplete todos
    4. Clicking on a user will bring up a detail page that contains all the todos associated with that user
    api  http://jsonplaceholder.typicode.com/todos
     */
    private var viewModel:MainVM = MainVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        userList.layoutManager = layoutManager
    }

    override fun onStart() {
        super.onStart()
        progressBar.visibility = View.VISIBLE
        viewModel.getData().observe(this, Observer<List<TodoUser>>{
            progressBar.visibility = View.GONE
            val adapter = UserAdapter(it!!)
            userList.adapter = adapter
        } )
    }

    //Adapter class
    inner class UserAdapter(val users:List<TodoUser>):RecyclerView.Adapter<TodoUserViewholder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TodoUserViewholder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.user_row,  null)
            val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.setLayoutParams(lp)
            return TodoUserViewholder(view)
        }

        override fun getItemCount(): Int {
            return users.size
        }

        override fun onBindViewHolder(holder: TodoUserViewholder, pos: Int) {
            val user = users[pos]
            holder.userId.text = user.userId
            holder.inCount.text = "${user.incompleteCount}"
            holder.viewItem.setOnClickListener{
                App.setUser(user.userId)
                startActivity(Intent(holder.viewItem.context, TodoDetailsActivity::class.java))
            }
        }
    }

    inner class TodoUserViewholder(val viewItem: View):RecyclerView.ViewHolder(viewItem){
        var userId:TextView = viewItem.findViewById(R.id.userId)
        var inCount:TextView = viewItem.findViewById(R.id.inCount)

    }
}
