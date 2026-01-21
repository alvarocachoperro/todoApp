package org.example.todoapp.app.repository

import org.example.todoapp.domain.Task

interface TaskRepository {
    fun saveTask(task: Task): Task

    fun getLast5Tasks(): List<Task>

    fun findById(id: String): Task?

    fun count(): Long
}
