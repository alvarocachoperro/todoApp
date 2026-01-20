package org.example.todoapp.app.service.impl

import org.example.todoapp.app.repository.TaskRepository
import org.example.todoapp.app.service.TaskService
import org.example.todoapp.domain.Task

class TaskServiceImpl( private val repository: TaskRepository) : TaskService {
    override suspend fun saveTask(task: Task): Long {
        return repository.saveTask(task)
    }

    override suspend fun getLast5Tasks(): List<Task> {
        return repository.getLast5Tasks()
    }

}