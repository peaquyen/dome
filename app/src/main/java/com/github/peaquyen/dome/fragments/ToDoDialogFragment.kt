package com.github.peaquyen.dome.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.peaquyen.dome.databinding.FragmentToDoDialogBinding
import com.github.peaquyen.dome.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class ToDoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentToDoDialogBinding
    private var listener: OnDialogNextBtnClickListener? = null
    private var toDoData: ToDoData? = null

    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"
        @JvmStatic
        fun newInstance(taskId: String, taskTitle: String, taskDescription: String, startDateTime: Long, endDateTime: Long, done: Boolean) =
            ToDoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("taskTitle", taskTitle)
                    putString("taskDescription", taskDescription)
                    putLong("startDateTime", startDateTime)
                    putLong("endDateTime", endDateTime)
                    putBoolean("done", done)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("taskTitle").toString(),
                arguments?.getString("taskDescription").toString(),
                arguments?.getLong("startDateTime") ?: 0L,
                arguments?.getLong("endDateTime") ?: 0L,
                arguments?.getBoolean("done") ?: false
            )
            binding.taskTitleEt.setText(toDoData?.taskTitle)
            binding.taskDescriptionEt.setText(toDoData?.taskDescription)
            binding.startDateTimeEt.setText(SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(toDoData!!.startDateTime)))
            binding.endDateTimeEt.setText(SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(toDoData!!.endDateTime)))
            binding.doneCheckbox.isChecked = toDoData!!.done
        }

        binding.startDateTimeEt.setOnClickListener { showDateTimePicker(binding.startDateTimeEt) }
        binding.endDateTimeEt.setOnClickListener { showDateTimePicker(binding.endDateTimeEt) }

        binding.todoClose.setOnClickListener {
            dismiss()
        }

        binding.todoNextBtn.setOnClickListener {
            val taskTitle = binding.taskTitleEt.text.toString()
            val taskDescription = binding.taskDescriptionEt.text.toString()
            val startDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(binding.startDateTimeEt.text.toString())?.time ?: 0L
            val endDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(binding.endDateTimeEt.text.toString())?.time ?: 0L
            val done = binding.doneCheckbox.isChecked

            if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                if (toDoData == null) {
                    listener?.saveTask(taskTitle, taskDescription, startDateTime, endDateTime, done, binding.taskTitleEt)
                } else {
                    toDoData!!.taskTitle = taskTitle
                    toDoData!!.taskDescription = taskDescription
                    toDoData!!.startDateTime = startDateTime
                    toDoData!!.endDateTime = endDateTime
                    toDoData!!.done = done
                    listener?.updateTask(toDoData!!, binding.taskTitleEt)
                }
            }
        }
    }

    private fun showDateTimePicker(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                editText.setText(SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(calendar.time))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    interface OnDialogNextBtnClickListener {
        fun saveTask(taskTitle: String, taskDescription: String, startDateTime: Long, endDateTime: Long, done: Boolean, todoEdit: TextInputEditText)
        fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText)
    }
}
