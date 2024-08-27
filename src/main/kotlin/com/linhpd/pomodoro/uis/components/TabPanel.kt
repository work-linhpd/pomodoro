package com.linhpd.pomodoro.uis.components

import com.intellij.openapi.components.service
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.linhpd.pomodoro.models.enums.PomodoroRound
import com.linhpd.pomodoro.services.ConfigService
import com.linhpd.pomodoro.services.SoundService
import com.linhpd.pomodoro.uis.widgets.ProgressButton
import com.linhpd.pomodoro.utils.Utils.Companion.calculatePercentage
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.SwingConstants
import javax.swing.Timer

class TabPanel(
    val round: PomodoroRound,
    private val onTimerCompleteCallBack: () -> Unit
) : JBPanel<TabPanel>(BorderLayout(0, 20)) {
    companion object {
        private const val PAUSE_TEXT_LABEL = "PAUSE"
        private const val START_TEXT_LABEL = "START"
    }

    private val soundService = service<SoundService>()
    private val configService = service<ConfigService>()
    private val roundInSeconds = configService.roundInSeconds(round)


    private val timerLabel = JBLabel(convertSecondsToMinuteFormat(roundInSeconds), SwingConstants.CENTER).apply {
        font = Font("Arial", Font.BOLD, 40)
    }
    private val button: ProgressButton


    private var timeLeft = roundInSeconds
    private lateinit var timer: Timer

    init {
        if (roundInSeconds == 0) {
            onTimerCompleteCallBack()
        }
        add(timerLabel, BorderLayout.CENTER)
        button = ProgressButton(START_TEXT_LABEL).apply {
            addActionListener {
                if (::timer.isInitialized && timer.isRunning) {
                    pauseTimer()
                } else {
                    startTimer()
                }
            }
        }
        add(button, BorderLayout.SOUTH)
    }

    private fun startTimer() {
        button.text = PAUSE_TEXT_LABEL
        timer = Timer(1000) { // Fire every 1 second
            if (timeLeft > 0) {
                timeLeft--
                timerLabel.text = convertSecondsToMinuteFormat(timeLeft)
                button.progress = calculatePercentage(roundInSeconds - timeLeft, roundInSeconds)
            } else {
                onTimerComplete()
            }
        }
        timer.start()
    }

    private fun pauseTimer() {
        timer.stop()
        button.text = START_TEXT_LABEL
    }

    private fun onTimerComplete() {
        timer.stop()
        button.text = START_TEXT_LABEL
        resetTimer()
        onTimerCompleteCallBack()
        soundService.playNotify()
    }

    private fun resetTimer() {
        timeLeft = roundInSeconds
        timerLabel.text = convertSecondsToMinuteFormat(timeLeft)
        button.progress = 0
    }

    private fun convertSecondsToMinuteFormat(seconds: Int): String {
        require(seconds >= 0) { "seconds must be larger than or equal 0" }
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}