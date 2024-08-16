package com.linhpd.pomodoro.services

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

interface NotificationService {
    fun showInformationMessage(project: Project, message: String)
}

class NotificationServiceImpl : NotificationService {

    companion object {
        private const val NOTIFY_GROUP_ID = "Pomodoro"
        private const val NOTIFY_TITLE = "Pomodoro"
    }

    override fun showInformationMessage(project: Project, message: String) {
        val notification = Notification(
            NOTIFY_GROUP_ID,
            NOTIFY_TITLE,
            message,
            NotificationType.INFORMATION
        )
        notification.notify(project)
    }
}