package com.linhpd.pomodoro.uis.widgets

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JButton
import javax.swing.UIManager

class ProgressButton(text: String) : JButton(text) {
    var progress: Int = 0
        set(value) {
            field = value.coerceIn(0, 100)
            repaint()
        }

    private val lightThemeColor = Color(52, 152, 219) // Bright blue
    private val darkThemeColor = Color(41, 128, 185)  // Slightly darker blue

    init {
        isContentAreaFilled = false
    }

    override fun paintComponent(g: Graphics) {
        val g2d = g.create() as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Determine if we're in dark mode
        val isDarkMode = UIManager.getColor("Panel.background")?.let {
            it.red + it.green + it.blue < 3 * 128
        } ?: false

        // Choose color based on theme
        val progressColor = if (isDarkMode) darkThemeColor else lightThemeColor

        // Paint the progress as background
        g2d.color = progressColor
        val progressWidth = (width * (progress / 100.0)).toInt()
        g2d.fillRect(0, 0, progressWidth, height)

        // Paint the remaining background
        g2d.color = background
        g2d.fillRect(progressWidth, 0, width - progressWidth, height)

        // Paint the text
        g2d.color = foreground
        val fm = g2d.fontMetrics
        val textBounds = fm.getStringBounds(text, g2d)
        val textX = ((width - textBounds.width) / 2).toInt()
        val textY = ((height - textBounds.height) / 2 + fm.ascent).toInt()
        g2d.drawString(text, textX, textY)

        g2d.dispose()
    }
}