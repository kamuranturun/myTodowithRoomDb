package com.kamuran.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter constructor(var todoList:ArrayList<ModelTodo>,
                                  private val mItemClickListener: ItemClickListener,
):RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    interface ItemClickListener {

        fun deleteItem(modelTodo: ModelTodo)
        fun updateItem(modelTodo: ModelTodo)
        fun detailItem(modelTodo: ModelTodo)
        fun changeTodostatus(todoId:Int, isCompleted:Boolean)
    }

    private var deleteItemPosition:Int= -1
    private var updatedItemPosition:Int= -1


  inner  class TodoViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
      val statusCbx= itemView.findViewById<CheckBox>(R.id.status_cbx)
      val deleteBtn= itemView.findViewById<ImageView>(R.id.todo_delete_btn)
      val updateBtn= itemView.findViewById<ImageView>(R.id.todo_edit_btn)
      val detailBtn= itemView.findViewById<ImageView>(R.id.detail_todo_btn)

      init {
          deleteBtn.setOnClickListener{
              deleteItemPosition=position
              mItemClickListener.deleteItem(todoList[deleteItemPosition])
          }

          updateBtn.setOnClickListener {
              updatedItemPosition=position
              mItemClickListener.updateItem(todoList[position])
          }

          detailBtn.setOnClickListener {
              mItemClickListener.detailItem(todoList[position])
          }

          statusCbx.setOnCheckedChangeListener { buttonView, isChecked ->
              mItemClickListener.changeTodostatus(todoList[position].todoId, isChecked)
          }

      }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_todo,parent,false)
        return TodoViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoName= holder.itemView.findViewById<TextView>(R.id.todo_tv)
        val rowId= holder.itemView.findViewById<TextView>(R.id.tv_ıd)
        val checkBox= holder.itemView.findViewById<CheckBox>(R.id.status_cbx)

        todoName.text= todoList[position].todoName
        rowId.text=(position+1).toString()
        checkBox.isChecked=todoList[position].status




    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun itemAdded(todoModel: ModelTodo) {
        todoList.add(todoModel)
       notifyDataSetChanged()


    }

    fun itemDeleted(modelTodo: ModelTodo) {

        todoList.remove(modelTodo)
        notifyItemRemoved(deleteItemPosition)


    }

    fun itemUpdated(modelTodo: ModelTodo) {
        todoList[updatedItemPosition]=modelTodo
        notifyItemChanged(updatedItemPosition)

    }
}