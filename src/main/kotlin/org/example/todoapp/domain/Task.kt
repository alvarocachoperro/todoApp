package org.example.todoapp.domain

data class Task (
    val id: String? = null,
    val titulo: String,
    val descripcion: String,
    val prioridad: Prioridad,
    val creado: Long = System.currentTimeMillis()
    )
