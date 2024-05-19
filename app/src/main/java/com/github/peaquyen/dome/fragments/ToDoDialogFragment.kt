package com.github.peaquyen.dome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.peaquyen.dome.R
import com.github.peaquyen.dome.databinding.FragmentToDoDialogBinding
import com.github.peaquyen.dome.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText


class ToDoDialogFragment : DialogFragment() {

    private lateinit var binding:FragmentToDoDialogBinding
    private var listener : OnDialogNextBtnClickListener? = null
    private var toDoData: ToDoData? = null


    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            ToDoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentToDoDialogBinding.inflate(inflater , container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){

            toDoData = ToDoData(arguments?.getString("taskId").toString() ,arguments?.getString("task").toString())
            binding.taskTitleEt.setText(toDoData?.taskTitle)
        }


        binding.todoClose.setOnClickListener {
            dismiss()
        }

        binding.todoNextBtn.setOnClickListener {

            val todoTask = binding.taskTitleEt.text.toString()
            if (todoTask.isNotEmpty()){
                if (toDoData == null){
                    listener?.saveTask(todoTask , binding.taskTitleEt)
                }else{
                    toDoData!!.taskTitle = todoTask
                    listener?.updateTask(toDoData!!, binding.taskTitleEt)
                }

            }
        }
    }

    interface OnDialogNextBtnClickListener{
        fun saveTask(todoTask:String , todoEdit:TextInputEditText)
        fun updateTask(toDoData: ToDoData , todoEdit:TextInputEditText)
    }

}