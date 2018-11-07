package com.tinle.mytodos.data

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["userId"])
data class TodoUser(
        val userId:String,
        var incompleteCount:Int
)