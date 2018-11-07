package com.tinle.mytodos

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.tinle.mytodos.data.TodoDb

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        db  =  Room.databaseBuilder(this, TodoDb::class.java, "the_db")
                .fallbackToDestructiveMigration()
                .build()
    }

    companion object {
        private var selectedUser = ""
        private var context:Context? = null
        private var db:TodoDb? = null

        fun getcontext():Context? {
           return context
        }

        fun getDb():TodoDb?{
            return db
        }

        fun setUser(userId:String) {
            selectedUser = userId
        }

        fun getSelectedUser():String{
            return selectedUser
        }
    }

}