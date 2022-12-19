package com.kamuran.mytodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener, TodoListAdapter.ItemClickListener {

    private lateinit var addOrUpdateTodoBtn:Button
    private var isUpdate:Boolean=false
    private lateinit var todoListRv:RecyclerView
    private lateinit var todoNameEdt:EditText
    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var todoDatabase:DatabaseTodo
    private lateinit var updateModelTodo: ModelTodo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addOrUpdateTodoBtn=findViewById(R.id.add_or_update_todo_btn)
        addOrUpdateTodoBtn.setOnClickListener(this)
        todoListRv= findViewById(R.id.recyclerview_todo)
        todoNameEdt=findViewById(R.id.todo_edittext)


        todoDatabase=DatabaseTodo.getTodoDatabase(this)!!
        val todoList=todoDatabase.getTodoDao().getAllTodo()

        todoListRv.layoutManager=LinearLayoutManager(this)
        todoListRv.setHasFixedSize(true)

        todoListAdapter= TodoListAdapter(todoList as ArrayList, this)
        todoListRv.adapter=todoListAdapter




    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add_or_update_todo_btn->{
                if (isUpdate){
                    updateTodoItem()
                }else{
                    addTodoItem()

                }
            }

        }
    }

    private fun addTodoItem() {

        val todo= getTodoNameEdt()
        val todoModel= ModelTodo(todoName = todo)
        todoDatabase.getTodoDao().addTodo(todoModel)
        todoListAdapter.itemAdded(todoModel)
        setTodoNameEdtText("")

    }

    private fun getTodoNameEdt(): String {
        return todoNameEdt.text.toString()
    }

    private fun updateTodoItem() {

        val todoName= getTodoNameEdt()
        todoDatabase.getTodoDao().updateTodo(todoName,updateModelTodo.todoId)
        isUpdate=false
        setAddOrUpdateTodoBtnText("Ekle")
        todoListAdapter.itemUpdated(ModelTodo(updateModelTodo.todoId,todoName))
        setTodoNameEdtText("")



    }

    private fun setTodoNameEdtText(text: String) {
        todoNameEdt.setText(text)
    }

    override fun deleteItem(modelTodo: ModelTodo) {

        todoDatabase.getTodoDao().delete(modelTodo.todoId)
        todoListAdapter.itemDeleted(modelTodo)

    }

    override fun updateItem(modelTodo: ModelTodo) {
        updateModelTodo=modelTodo
        todoNameEdt.setText(modelTodo.todoName)
        setAddOrUpdateTodoBtnText("Güncelle")
        isUpdate=true

            }

    private fun setAddOrUpdateTodoBtnText(text:String) {
        addOrUpdateTodoBtn.text=text
    }

    override fun detailItem(modelTodo: ModelTodo) {

        val detailTodoName=modelTodo.todoName
        val intent= Intent(this,TodoDetailActivity::class.java)
        intent.putExtra("todo",detailTodoName)
        startActivity(intent)

    }

    override fun changeTodostatus(todoId: Int, isCompleted: Boolean) {

        todoDatabase.getTodoDao().changeTodoStatus(todoId,isCompleted)
        println(isCompleted)
        println(todoId)

    }
}