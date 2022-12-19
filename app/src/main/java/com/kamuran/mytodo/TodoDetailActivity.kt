package com.kamuran.mytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TodoDetailActivity : AppCompatActivity() {

    lateinit var tvDetail:TextView
    lateinit var tvCount:TextView
    private lateinit var todoDatabase:DatabaseTodo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_detail)

        tvDetail=findViewById(R.id.tv_detail)
        tvCount=findViewById(R.id.tv_detail_count)

        todoDatabase= DatabaseTodo.getTodoDatabase(this)!!


        val intent=intent.extras
        val todoLisCount= getTodoListCount()




        if(intent !=null){
            tvDetail.text="Merhaba   '${intent.getString("todo")}'   yapılacak işin var.."
            tvCount.text= "Bu gün yapılacak toplam  '$todoLisCount'  tane işin var"


        }
    }

    private fun getTodoListCount(): Int {
        return todoDatabase.getTodoDao().todoListCount()

    }
}