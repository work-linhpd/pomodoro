package com.linhpd.pomodoro.services

import com.linhpd.pomodoro.models.entities.Task

interface TaskService {
    val tasks: List<Task>
    fun addTask(task: Task)
    fun removeTask(task: Task)
    fun updateTask(task: Task)
    fun completeTask(task: Task)
}

class TaskServiceImpl : TaskService {
    override val tasks = mutableListOf<Task>()

    init {
        listOf(
            Task("Write code for feature A", "High priority", 3),
            Task("Fix bug in module B", "Check with QA", 2),
            Task("Prepare documentation for module C", "Low priority", 1),
        ).forEach { tasks.add(it) }
    }

    override fun addTask(task: Task) {
        tasks.add(task)
    }

    override fun removeTask(task: Task) {
        tasks.remove(task)
    }

    override fun updateTask(task: Task) {
        tasks.firstOrNull { it.id == task.id }?.let {
            tasks[tasks.indexOf(it)] = task
        }
    }

    override fun completeTask(task: Task) {
        task.isCompleted = true
    }
}