package org.example.todoapp.infra.dynamodb.mapper

import org.example.todoapp.domain.Prioridad
import org.example.todoapp.domain.Task
import org.example.todoapp.infra.dynamodb.dbo.TaskEntity
import java.util.UUID

object TaskMapper {
    
    fun toEntity(task: Task): TaskEntity = TaskEntity(
        id = task.id ?: "TASK#${UUID.randomUUID()}",
        title = task.titulo,
        description = task.descripcion,
        priority = task.prioridad.value,
        createdAt = task.creado
    )
    
    fun toDomain(entity: TaskEntity): Task = Task(
        id = entity.id,
        titulo = entity.title ?: "",
        descripcion = entity.description ?: "",
        prioridad = when(entity.priority) {  // ← Int → Enum
            1 -> Prioridad.BAJA
            2 -> Prioridad.MEDIA
            3 -> Prioridad.ALTA
            else -> Prioridad.MEDIA
        },
        creado = entity.createdAt ?: 0L
    )
}
