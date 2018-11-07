package com.tinle.mytodos.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Todo::class, TodoUser::class],
        version = 1)
abstract class TodoDb:RoomDatabase() {
    abstract fun todoDao():TodoDao
}