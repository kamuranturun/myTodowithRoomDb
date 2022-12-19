package com.kamuran.mytodo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ModelTodo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    var todoId: Int = 0,
    @ColumnInfo(name = "todo_name")
    var todoName: String,

    @ColumnInfo(name = "status")
    var status: Boolean = false
)
