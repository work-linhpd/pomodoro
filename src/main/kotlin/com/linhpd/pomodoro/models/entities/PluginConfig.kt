package com.linhpd.pomodoro.models.entities

import com.linhpd.pomodoro.models.enums.PomodoroRound

data class PluginConfig(
    val intervals: Map<PomodoroRound, Int> = mapOf(
        PomodoroRound.POMODORO to 25,
        PomodoroRound.SHORT_BREAK to 5,
        PomodoroRound.LONG_BREAK to 15,
    ),
    val autoStartBreak: Boolean = true,
    val autoStartPomodoro: Boolean = true,
    val longBreakInterval: Int = 4
)
