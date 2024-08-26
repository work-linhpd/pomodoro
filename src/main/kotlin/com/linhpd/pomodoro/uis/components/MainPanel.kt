package com.linhpd.pomodoro.uis.components

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTabbedPane
import com.linhpd.pomodoro.models.enums.PomodoroRound
import com.linhpd.pomodoro.services.ConfigService
import com.linhpd.pomodoro.services.NotificationService
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JSeparator

class MainPanel(private val project: Project) : JPanel() {

    private val notificationService = service<NotificationService>()
    private val configService = service<ConfigService>()
    private var pomodoroCount = 1
    private val tabbedPane = JBTabbedPane().apply {
        addTab("Pomodoro", TabPanel(PomodoroRound.POMODORO, ::moveToNextTab))
        addTab("Short Break", TabPanel(PomodoroRound.SHORT_BREAK, ::moveToNextTab))
        addTab("Long Break", TabPanel(PomodoroRound.LONG_BREAK, ::moveToNextTab))
    }

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(tabbedPane)
        add(JSeparator())
        add(SettingPanel(project))
        add(JSeparator())
        add(TasksPanel(project))
    }

    private fun moveToNextTab() {
        val nextTab = calculateNextRoundIndex(tabbedPane.selectedIndex)
        if (tabbedPane.selectedIndex == 0) {
            pomodoroCount++
        }
        tabbedPane.selectedIndex = nextTab
        notificationService.showInformationMessage(
            project,
            (tabbedPane.selectedComponent as TabPanel).round.notificationMessage
        )
    }


    private fun calculateNextRoundIndex(currentIndex: Int): Int {
        return when (currentIndex) {
            0 -> if (pomodoroCount % configService.config.longBreakInterval == 0) 2 else 1
            1, 2 -> 0
            else -> throw IndexOutOfBoundsException("currentIndex is invalid $currentIndex")
        }
    }
}