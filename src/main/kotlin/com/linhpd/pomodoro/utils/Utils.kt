package com.linhpd.pomodoro.utils

class Utils {
    companion object {
        fun calculatePercentage(current: Int, total: Int): Int {
            require(total != 0) { "total must not be zero" }
            return ((current.toDouble() / total) * 100).toInt()
        }
    }
}