package com.linhpd.pomodoro.models.enums

enum class PomodoroRound(val notificationMessage: String) {
    POMODORO("Time to works!"),
    SHORT_BREAK("Time for a break!"),
    LONG_BREAK("Time for long break!"),
}