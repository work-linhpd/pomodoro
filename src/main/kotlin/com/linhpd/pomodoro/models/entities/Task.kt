package com.linhpd.pomodoro.models.entities

import java.util.*

data class Task(
    val description: String,
    val note: String,
    val estimatedPomodoros: Int,
    val id: UUID = UUID.randomUUID(),
    var isCompleted: Boolean = false
)