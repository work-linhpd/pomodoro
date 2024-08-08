package com.linhpd.pomodoro.models.enums

enum class PomodoroRound(val seconds: Int, val notificationMessage: String) {
    POMODORO(5, "Time to works!"),
    SHORT_BREAK(3, "Time for a break!"),
    LONG_BREAK(4, "Time for long break!"),
}