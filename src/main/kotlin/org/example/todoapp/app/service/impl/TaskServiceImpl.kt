package org.example.todoapp.app.service.impl

import org.example.todoapp.app.repository.TaskRepository
import org.example.todoapp.app.service.TaskService
import org.example.todoapp.domain.Task
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl( private val repository: TaskRepository) : TaskService {
    override fun saveTask(task: Task): Task {
        return repository.saveTask(task)
    }

    override fun getLast5Tasks(): List<Task> {
        return repository.getLast5Tasks()
    }

    override fun getTasksById(id: String): Task? {
        return repository.findById(id)
    }

}