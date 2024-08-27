package com.linhpd.pomodoro.uis.components

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.linhpd.pomodoro.models.entities.PluginConfig
import com.linhpd.pomodoro.models.enums.PomodoroRound
import com.linhpd.pomodoro.services.ConfigService
import com.linhpd.pomodoro.services.NotificationService
import com.linhpd.pomodoro.uis.widgets.NumberField
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel

class SettingPanel(project: Project) : JBPanel<SettingPanel>() {

    private val configService = service<ConfigService>()
    private val notificationService = service<NotificationService>()
    private val intervalsTablePanel = IntervalsTablePanel(::updateSaveButtonState)
    private val autoStartBreakCheckBox = JBCheckBox().apply {
        this.isSelected = configService.config.autoStartBreak
    }
    private val autoStartPomodoroCheckBox = JBCheckBox().apply {
        this.isSelected = configService.config.autoStartPomodoro
    }
    private val longBreakIntervalTextField = NumberField(configService.config.longBreakInterval, ::updateSaveButtonState)
    private val saveButton = JButton("Save").apply {
        addActionListener {
            val config = PluginConfig(
                intervals = intervalsTablePanel.getData(),
                autoStartPomodoro = autoStartPomodoroCheckBox.isSelected,
                autoStartBreak = autoStartBreakCheckBox.isSelected,
                longBreakInterval = longBreakIntervalTextField.text.toInt(),
            )
            configService.updateConfig(config)
            notificationService.showInformationMessage(project, "Configuration has been updated")
        }
    }

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(intervalsTablePanel)
        add(createInputPanel("Auto Start Breaks", autoStartBreakCheckBox))
        add(createInputPanel("Auto Start Pomodoros", autoStartPomodoroCheckBox))
        add(createInputPanel("Long Break interval", longBreakIntervalTextField))

        add(saveButton)
    }

    fun updateSaveButtonState() {
        saveButton.isEnabled = allFieldsAreValid()
    }

    private fun allFieldsAreValid(): Boolean {
        return intervalsTablePanel.isAllInputsValid()
                && longBreakIntervalTextField.isValidInput()
    }

    private fun createInputPanel(label: String, inputComponent: JComponent): JBPanel<JBPanel<*>> {
        return JBPanel<JBPanel<*>>(BorderLayout()).apply {
            preferredSize = Dimension(preferredSize.width, 30)
            add(JBLabel(label), BorderLayout.WEST)
            add(inputComponent, BorderLayout.EAST)
        }
    }

}

class IntervalsTablePanel(updateButtonState: () -> Unit = {}) : JBPanel<IntervalsTablePanel>() {

    private val configService = service<ConfigService>()
    private val pomodoroTextField = NumberField(configService.roundInMinutes(PomodoroRound.POMODORO), updateButtonState)
    private val shortBreakTextField = NumberField(configService.roundInMinutes(PomodoroRound.SHORT_BREAK), updateButtonState, true)
    private val longBreakTextField = NumberField(configService.roundInMinutes(PomodoroRound.LONG_BREAK), updateButtonState, true)

    init {
        layout = GridLayout(2, 3, 20, 0)

        add(JBLabel("Pomodoro"))
        add(JBLabel("Short break"))
        add(JBLabel("Long break"))
        add(pomodoroTextField)
        add(shortBreakTextField)
        add(longBreakTextField)
        preferredSize = Dimension(preferredSize.width, pomodoroTextField.preferredSize.height * 2)
    }

    fun getData(): Map<PomodoroRound, Int> {
        return mapOf(
            PomodoroRound.POMODORO to pomodoroTextField.text.toInt(),
            PomodoroRound.SHORT_BREAK to shortBreakTextField.text.toInt(),
            PomodoroRound.LONG_BREAK to longBreakTextField.text.toInt()
        )
    }

    fun isAllInputsValid(): Boolean {
        return pomodoroTextField.isValidInput()
                && shortBreakTextField.isValidInput()
                && longBreakTextField.isValidInput()
    }
}

class VariableSettingsPanel : JPanel() {
    init {
        layout = GridBagLayout()
    }
}
