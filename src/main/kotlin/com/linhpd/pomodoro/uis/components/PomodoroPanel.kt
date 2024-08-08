package com.linhpd.pomodoro.uis.components

import com.intellij.notification.Notification
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTabbedPane
import com.linhpd.pomodoro.models.enums.PomodoroRound
import javax.swing.BoxLayout
import javax.swing.JPanel

class PomodoroPanel(val project: Project) : JPanel() {
    private val longBreakInterval = 4
    private var pomodoroCount = 1
    private val tabbedPane = JBTabbedPane().apply {
        addTab("Pomodoro", PomodoroTabPanel(PomodoroRound.POMODORO, ::moveToNextTab))
        addTab("Short Break", PomodoroTabPanel(PomodoroRound.SHORT_BREAK, ::moveToNextTab))
        addTab("Long Break", PomodoroTabPanel(PomodoroRound.LONG_BREAK, ::moveToNextTab))
        alignmentX = LEFT_ALIGNMENT
        preferredSize = preferredSize.apply { width = Int.MAX_VALUE }
    }
    private val notificationGroup = NotificationGroup("Pomodoro", NotificationDisplayType.BALLOON, true)

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(tabbedPane)
    }

    private fun moveToNextTab() {
        val nextTab = calculateNextRoundIndex(tabbedPane.selectedIndex)
        if (tabbedPane.selectedIndex == 0) {
            pomodoroCount++
        }
        tabbedPane.selectedIndex = nextTab
        showNotification((tabbedPane.selectedComponent as PomodoroTabPanel).round.notificationMessage)
    }


    private fun calculateNextRoundIndex(currentIndex: Int): Int {
        return when (currentIndex) {
            0 -> if (pomodoroCount % longBreakInterval == 0) 2 else 1
            1, 2 -> 0
            else -> throw IndexOutOfBoundsException("currentIndex is invalid $currentIndex")
        }
    }

    private fun showNotification(message: String) {
        val notification = Notification(
            notificationGroup.displayId,
            "Pomodoro",
            message,
            NotificationType.INFORMATION
        )
        notification.notify(project)
    }
}