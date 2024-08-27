package com.linhpd.pomodoro.uis.toolwindows

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.linhpd.pomodoro.uis.components.MainPanel
import java.awt.CardLayout
import javax.swing.JPanel


class PomodoroToolWindow(project: Project) : JPanel(CardLayout()) {
    init {
        add(JBScrollPane(MainPanel(project)))
    }
}