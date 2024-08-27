package com.linhpd.pomodoro.uis.components

import com.intellij.icons.AllIcons
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.linhpd.pomodoro.models.entities.Task
import com.linhpd.pomodoro.services.TaskService
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JButton

class TasksPanel(val project: Project) : JBPanel<TasksPanel>() {

    private val taskService = service<TaskService>()
    private val tasksContainer = JBPanel<JBPanel<*>>().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
    }

    init {
        layout = BorderLayout()
        updateTaskList()
        add(tasksContainer, BorderLayout.CENTER)
        add(createAddTaskButton(), BorderLayout.SOUTH)
    }

    private fun updateTaskList() {
        tasksContainer.removeAll()
        taskService.tasks.forEach { tasksContainer.add(TaskItemPanel(it)) }
    }

    private fun createAddTaskButton(): JButton {
        return JButton().apply {
            text = "Add Task"
            icon = AllIcons.General.InlineAdd
            addActionListener {
                val task = Task(description = "New Task", estimatedPomodoros = 0, note = System.currentTimeMillis().toString())
                taskService.addTask(task)
                updateTaskList()
                tasksContainer.revalidate()
                tasksContainer.repaint()
            }
        }
    }
}

class TaskItemPanel(task: Task) : JBPanel<TaskItemPanel>() {
    private val checkBox = JBCheckBox().apply {
        isSelected = task.isCompleted
        addActionListener {
            isEnabled = !isSelected
        }
    }

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        border = BorderFactory.createLineBorder(JBColor.white)
        add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
            add(JBPanel<JBPanel<*>>().apply {
                layout = FlowLayout()
                add(checkBox)
                add(JBLabel(task.description))
            }, BorderLayout.WEST)
            add(JBLabel(task.estimatedPomodoros.toString()), BorderLayout.EAST)
        })
        add(JBPanel<JBPanel<*>>().apply {
            preferredSize = Dimension(preferredSize.width, preferredSize.height + 20)
            add(JBLabel(task.note))
        })
    }
}

