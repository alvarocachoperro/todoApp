package org.example.todoapp.app.repository

import org.example.todoapp.domain.Task

interface TaskRepository {
    suspend fun saveTask(task: Task): Long
    suspend fun getLast5Tasks(): List<Task>
}