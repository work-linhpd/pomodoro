package com.linhpd.pomodoro.models.entities

data class Task(
    val description: String,
    val note: String,
    val estimatedPomodoros: Int,
)