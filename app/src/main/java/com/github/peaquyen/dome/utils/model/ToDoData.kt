package com.github.peaquyen.dome.utils.model
data class ToDoData(
    var taskId: String,
    var taskTitle: String,
    var taskDescription: String,
    var startDateTime: Long,
    var endDateTime: Long,
    var done: Boolean
)
