package org.example.todoapp.app.service

import org.example.todoapp.domain.Task

interface TaskService {
    suspend fun saveTask(task: Task): Long
    suspend fun getLast5Tasks(): List<Task>
}