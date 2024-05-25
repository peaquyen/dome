package com.github.peaquyen.dome.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.peaquyen.dome.databinding.FragmentHomeBinding
import com.github.peaquyen.dome.utils.adapter.TaskAdapter
import com.github.peaquyen.dome.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(), ToDoDialogFragment.OnDialogNextBtnClickListener,
    TaskAdapter.TaskAdapterInterface {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private var frag: ToDoDialogFragment? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var toDoItemList: MutableList<ToDoData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        //get data from firebase
        getTaskFromFirebase()

        binding.addTaskBtn.setOnClickListener {
            if (frag != null)
                childFragmentManager.beginTransaction().remove(frag!!).commit()
            frag = ToDoDialogFragment()
            frag!!.setListener(this)
            frag!!.show(childFragmentManager, ToDoDialogFragment.TAG)
        }

        // Logout
        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun getTaskFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                toDoItemList.clear()
                for (taskSnapshot in snapshot.children) {
                    val taskId = taskSnapshot.key ?: continue
                    val taskTitle = taskSnapshot.child("taskTitle").getValue(String::class.java) ?: ""
                    val taskDescription = taskSnapshot.child("taskDescription").getValue(String::class.java) ?: ""
                    val startDateTime = taskSnapshot.child("startDateTime").getValue(Long::class.java) ?: 0L
                    val endDateTime = taskSnapshot.child("endDateTime").getValue(Long::class.java) ?: 0L
                    val done = taskSnapshot.child("done").getValue(Boolean::class.java) ?: false
                    val todoTask = ToDoData(taskId, taskTitle, taskDescription, startDateTime, endDateTime, done)
                    toDoItemList.add(todoTask)
                }
                Log.d(TAG, "onDataChange: $toDoItemList")
                taskAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser!!.uid
        database = Firebase.database.reference.child("Tasks").child(authId)

        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)

        toDoItemList = mutableListOf()
        taskAdapter = TaskAdapter(toDoItemList)
        taskAdapter.setListener(this)
        binding.mainRecyclerView.adapter = taskAdapter
    }

    override fun saveTask(taskTitle: String, taskDescription: String, startDateTime: Long, endDateTime: Long, done: Boolean, todoEdit: TextInputEditText) {
        val newTaskRef = database.push()
        val task = ToDoData(newTaskRef.key!!, taskTitle, taskDescription, startDateTime, endDateTime, done)
        newTaskRef.setValue(task).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_SHORT).show()
                todoEdit.text = null
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        frag!!.dismiss()
    }

    override fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText) {
        val map = mapOf(
            "taskTitle" to toDoData.taskTitle,
            "taskDescription" to toDoData.taskDescription,
            "startDateTime" to toDoData.startDateTime,
            "endDateTime" to toDoData.endDateTime,
            "done" to toDoData.done
        )
        database.child(toDoData.taskId).updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
            frag!!.dismiss()
        }
    }

    override fun onDeleteItemClicked(toDoData: ToDoData, position: Int) {
        database.child(toDoData.taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditItemClicked(toDoData: ToDoData, position: Int) {
        if (frag != null)
            childFragmentManager.beginTransaction().remove(frag!!).commit()

        frag = ToDoDialogFragment.newInstance(
            toDoData.taskId,
            toDoData.taskTitle,
            toDoData.taskDescription,
            toDoData.startDateTime,
            toDoData.endDateTime,
            toDoData.done
        )
        frag!!.setListener(this)
        frag!!.show(childFragmentManager, ToDoDialogFragment.TAG)
    }

    override fun onTaskStatusChanged(toDoData: ToDoData, position: Int) {
        val map = mapOf("done" to toDoData.done)
        database.child(toDoData.taskId).updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Task Status Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
        findNavController().navigate(action)
    }
}
