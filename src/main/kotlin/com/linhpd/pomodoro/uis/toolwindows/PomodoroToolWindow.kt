package com.linhpd.pomodoro.uis.toolwindows

import com.intellij.openapi.project.Project
import com.linhpd.pomodoro.uis.components.PomodoroPanel
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel


class PomodoroToolWindow(project: Project) : JPanel(BorderLayout()) {
    init {
        add(PomodoroPanel(project).apply {
            alignmentX = LEFT_ALIGNMENT
            maximumSize = Dimension(this.width, preferredSize.height)
        }, BorderLayout.NORTH)
    }
}