package com.tinle.mytodos.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(primaryKeys = ["id"],
        foreignKeys = [ ForeignKey(
                entity = TodoUser::class,
                parentColumns = ["userId"],
                childColumns = ["userId"],
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE

        )]
        )
data class Todo(
        val userId:String,
        val id:String,
        val title:String,
        val completed:Boolean
        )