package com.github.peaquyen.dome.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.peaquyen.dome.databinding.EachTodoItemBinding
import com.github.peaquyen.dome.utils.model.ToDoData
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private val list: MutableList<ToDoData>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val toDoData: ToDoData? = null
    private val TAG = "TaskAdapter"
    private var listener: TaskAdapterInterface? = null

    fun setListener(listener: TaskAdapterInterface) {
        this.listener = listener
    }

    class TaskViewHolder(val binding: EachTodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.taskTitle
                binding.todoDescription.text = this.taskDescription
                binding.startDateTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(this.startDateTime))
                binding.endDateTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(this.endDateTime))
                binding.doneCheckbox.isChecked = this.done

                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this, position)
                }

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this, position)
                }

                binding.doneCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    this.done = isChecked
                    listener?.onTaskStatusChanged(this, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface TaskAdapterInterface {
        fun onDeleteItemClicked(toDoData: ToDoData, position: Int)
        fun onEditItemClicked(toDoData: ToDoData, position: Int)
        fun onTaskStatusChanged(toDoData: ToDoData, position: Int)
    }
}
