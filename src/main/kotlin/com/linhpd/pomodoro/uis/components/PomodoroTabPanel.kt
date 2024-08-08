package com.linhpd.pomodoro.uis.components

import com.linhpd.pomodoro.models.enums.PomodoroRound
import com.linhpd.pomodoro.utils.Utils.Companion.calculatePercentage
import java.awt.BorderLayout
import java.awt.Font
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.Timer

class PomodoroTabPanel(
    val round: PomodoroRound,
    private val onTimerCompleteCallBack: () -> Unit
) : JPanel(BorderLayout(0, 20)) {
    companion object {
        private const val PAUSE_TEXT_LABEL = "PAUSE"
        private const val START_TEXT_LABEL = "START"
    }

    private val timerLabel: JLabel = JLabel(convertSecondsToMinuteFormat(round.seconds), SwingConstants.CENTER).apply {
        font = Font("Arial", Font.BOLD, 40)
    }
    private val button: ProgressButton
    private var timeLeft = round.seconds
    private lateinit var timer: Timer

    init {
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
                button.progress = calculatePercentage(round.seconds - timeLeft, round.seconds)
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
        playSound()
    }

    private fun resetTimer() {
        timeLeft = round.seconds
        timerLabel.text = convertSecondsToMinuteFormat(timeLeft)
        button.progress = 0
    }

    private fun convertSecondsToMinuteFormat(seconds: Int): String {
        require(seconds >= 0) { "seconds must be larger than or equal 0" }
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    private fun playSound() {
        val soundResource: URL? = this::class.java.getResource("/sounds/classic-alarm.wav")
        if (soundResource != null) {
            val tempFile = File("temp_sound.wav")
            tempFile.deleteOnExit()

            val inputStream = soundResource.openStream()
            val outputStream = FileOutputStream(tempFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()


            val soundFile = File(tempFile.path)
            val audioIn = AudioSystem.getAudioInputStream(soundFile)
            val clip = AudioSystem.getClip()
            clip.open(audioIn)
            clip.start()
        } else {
            println("Sound file not found")
        }
    }
}