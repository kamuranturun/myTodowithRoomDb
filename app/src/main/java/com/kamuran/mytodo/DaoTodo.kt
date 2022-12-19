package com.kamuran.mytodo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoTodo {

    @Insert
    fun addTodo(todo:ModelTodo)

    @Query("SELECT * FROM todo_table ORDER BY todo_id DESC")
    fun getAllTodo():List<ModelTodo>

    @Query("UPDATE todo_table SET todo_name=:todoName WHERE todo_id=:todoId")
    fun updateTodo(todoName:String,todoId:Int)

    @Query("DELETE FROM todo_table WHERE todo_id=:todoId")
    fun delete(todoId:Int)

    @Query("SELECT COUNT(*) FROM todo_table")
    fun todoListCount():Int

    @Query("UPDATE todo_table SET status=:isCompleted WHERE todo_id=:todoId")
    fun changeTodoStatus(todoId:Int, isCompleted:Boolean)
}