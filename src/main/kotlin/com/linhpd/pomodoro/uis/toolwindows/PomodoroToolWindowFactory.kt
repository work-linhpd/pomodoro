package com.linhpd.pomodoro.uis.toolwindows

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class PomodoroToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val content = ContentFactory.getInstance().createContent(PomodoroToolWindow(project), "", false)
        toolWindow.contentManager.addContent(content)
    }

}