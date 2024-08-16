package com.linhpd.pomodoro.utils

class ValidationUtils {
    companion object {
        fun isValidNumber(text: String, allowZero: Boolean = false): Boolean {
            val value = text.toIntOrNull()
            return value != null && (value > 0 || (allowZero && value == 0)) && value <= 120
        }
    }
}