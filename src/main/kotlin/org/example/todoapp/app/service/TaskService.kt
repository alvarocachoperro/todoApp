package org.example.todoapp.app.service

import org.example.todoapp.domain.Task

interface TaskService {
    fun saveTask(task: Task): Task
    fun getLast5Tasks(): List<Task>
    fun getTasksById(id: String): Task?
}