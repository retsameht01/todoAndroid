package com.tinle.mytodos.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(vararg todos:Todo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg users:TodoUser)

    @Query("SELECT * FROM Todo WHERE userId = :uid")
    fun getTodos(uid:String):List<Todo>

    @Query("SELECT * FROM TodoUser")
    fun getUsers():List<TodoUser>

    @Query("DELETE FROM TodoUser")
    fun clearUsers()

}