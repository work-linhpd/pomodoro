package com.linhpd.pomodoro.uis.components

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.linhpd.pomodoro.models.entities.Task
import java.awt.BorderLayout
import java.awt.Component
import java.awt.FlowLayout
import javax.swing.*

class TasksPanel(val project: Project) : JPanel() {
    private val tasks = listOf(
        Task("Write code for feature A", "High priority", 3),
        Task("Fix bug in module B", "Check with QA", 2),
        Task("Prepare documentation for module C", "Low priority", 1),
    )
    private val taskList = JBList<TaskItemPanel>().apply {
        model = DefaultListModel<TaskItemPanel>().apply {
            tasks.forEach { addElement(TaskItemPanel(it)) }
        }
        cellRenderer = CustomListItemRenderer()
    }

    init {
        layout = BorderLayout()
        add(JBScrollPane(taskList), BorderLayout.CENTER)
    }
}

class CustomListItemRenderer : ListCellRenderer<TaskItemPanel> {
    override fun getListCellRendererComponent(
        list: JList<out TaskItemPanel>,
        value: TaskItemPanel,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        return value
    }
}

class TaskItemPanel(task: Task) : JBPanel<TaskItemPanel>() {
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        border = BorderFactory.createEmptyBorder(0, 10, 30, 10)
        add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
            add(JBPanel<JBPanel<*>>().apply {
                layout = FlowLayout()
                add(JCheckBox())
                add(JBLabel(task.description))
            }, BorderLayout.WEST)
            add(JBLabel(task.estimatedPomodoros.toString()), BorderLayout.EAST)
        })
        add(JBPanel<JBPanel<*>>().apply {
            add(JBLabel(task.note))
        })
    }
}

