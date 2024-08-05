package com.linhpd.pomodoro.ui.toolwindows

import com.intellij.ui.JBColor
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.event.ActionListener
import javax.swing.*


class PomodoroToolWindow : JPanel() {

    init {
        setLayout(BorderLayout(0, 20))


        val tabbedPane: JTabbedPane = JTabbedPane()
        tabbedPane.addTab("Pomodoro", createTimerPanel())
        tabbedPane.addTab("Short Break", JPanel())
        tabbedPane.addTab("Long Break", JPanel())
        add(tabbedPane, BorderLayout.NORTH)


        val timerLabel: JLabel = JLabel("25:00", SwingConstants.CENTER)
        timerLabel.setFont(Font("Arial", Font.BOLD, 48))
        add(timerLabel, BorderLayout.CENTER)


        val startButton: JButton = JButton("START")
        startButton.setBackground(JBColor.BLACK)
        startButton.setForeground(JBColor.WHITE)
        startButton.addActionListener(ActionListener {
            // Implement timer start logic
        })
        add(startButton, BorderLayout.SOUTH)
    }

    private fun createTimerPanel(): JPanel {
        val panel = JPanel()
        panel.background = Color(165, 42, 42) // Dark red color
        return panel
    }


}