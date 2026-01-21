
package org.example.todoapp.infra.dynamodb.adapters

import org.example.todoapp.app.repository.TaskRepository
import org.example.todoapp.domain.Prioridad
import org.example.todoapp.domain.Task
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
@Profile("local")
class DataInitializer(
    private val taskRepository: TaskRepository,
) {
    @EventListener(ApplicationReadyEvent::class)
    @Async
    fun initData() {
        if (taskRepository.count() == 0L) {
            listOf(
                Task(titulo = "Comprar leche", descripcion = "Urgente", prioridad = Prioridad.ALTA),
                Task(titulo = "Pasear perro", descripcion = "Mañana", prioridad = Prioridad.MEDIA),
                Task(titulo = "Llamar mamá", descripcion = "Domingo", prioridad = Prioridad.BAJA),
            ).forEach {
                taskRepository.saveTask(it)
            }
            println("🌟 Datos de prueba insertados!")
        }
    }
}
