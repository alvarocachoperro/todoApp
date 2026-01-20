package org.example.todoapp.domain

import java.util.UUID

data class Task (
    val titulo: String,
    val descripcion: String,
    val prioridad: Prioridad
    )
