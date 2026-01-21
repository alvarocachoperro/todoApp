package org.example.todoapp.app.controller

import org.example.todoapp.generated.api.DefaultApi
import org.example.todoapp.generated.domain.CreateTaskRequest
import org.example.todoapp.generated.domain.Task as GeneratedTask
import org.example.todoapp.app.service.TaskService
import org.example.todoapp.domain.Task as DomainTask
import org.example.todoapp.domain.Prioridad
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.runBlocking

@RestController
class TaskController(private val taskService: TaskService) : DefaultApi {

    override fun createTask(createTaskRequest: CreateTaskRequest): ResponseEntity<GeneratedTask> {
        val domainTask = DomainTask(
            titulo = createTaskRequest.title,
            descripcion = createTaskRequest.description,
            prioridad = when (createTaskRequest.priority) {
                CreateTaskRequest.Priority.LOW -> Prioridad.BAJA
                CreateTaskRequest.Priority.MEDIUM -> Prioridad.MEDIA
                CreateTaskRequest.Priority.HIGH -> Prioridad.ALTA
            }
        )
        
        // El servicio es suspend, pero el controlador generado no lo es
        runBlocking {
            taskService.saveTask(domainTask)
        }

        val responseTask = GeneratedTask(
            title = domainTask.titulo,
            description = domainTask.descripcion,
            priority = GeneratedTask.Priority.valueOf(createTaskRequest.priority.name)
        )

        return ResponseEntity.ok(responseTask)
    }

    override fun getAllTasks(): ResponseEntity<List<GeneratedTask>> {
        val tasks = runBlocking {
            taskService.getLast5Tasks()
        }

        val responseTasks = tasks.map { domainTask ->
            GeneratedTask(
                title = domainTask.titulo,
                description = domainTask.descripcion,
                priority = when (domainTask.prioridad) {
                    Prioridad.BAJA -> GeneratedTask.Priority.LOW
                    Prioridad.MEDIA -> GeneratedTask.Priority.MEDIUM
                    Prioridad.ALTA -> GeneratedTask.Priority.HIGH
                }
            )
        }

        return ResponseEntity.ok(responseTasks)
    }

    override fun getTaskById(id: String): ResponseEntity<GeneratedTask> {
        val task = taskService.getTasksById(id)

        val responseTask = GeneratedTask(
            title = task?.titulo,
            description = task?.descripcion,
            priority = when (task?.prioridad) {
                Prioridad.BAJA -> GeneratedTask.Priority.LOW
                Prioridad.MEDIA -> GeneratedTask.Priority.MEDIUM
                Prioridad.ALTA -> GeneratedTask.Priority.HIGH
                else -> {}
            } as GeneratedTask.Priority?
        )
        return ResponseEntity.ok(responseTask)
    }
}
