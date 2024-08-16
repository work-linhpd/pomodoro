package com.linhpd.pomodoro.uis.widgets

import com.linhpd.pomodoro.utils.ValidationUtils
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.AbstractDocument
import javax.swing.text.AttributeSet
import javax.swing.text.DocumentFilter

class NumberField(
    initValue: Int?,
    updateButtonState: () -> Unit = {},
    private val allowZero: Boolean = false,
) : JTextField() {
    init {
        if (initValue != null) {
            text = initValue.toString()
        }
        (document as? AbstractDocument)?.documentFilter = object : DocumentFilter() {
            override fun insertString(fb: FilterBypass, offset: Int, string: String?, attr: AttributeSet?) {
                if (isValidInput(fb.document.getText(0, fb.document.length) + string)) {
                    super.insertString(fb, offset, string, attr)
                }
            }

            override fun replace(fb: FilterBypass, offset: Int, length: Int, text: String?, attrs: AttributeSet?) {
                val currentText = fb.document.getText(0, fb.document.length)
                val newText = currentText.substring(0, offset) + (text ?: "") + currentText.substring(offset + length)
                if (isValidInput(newText)) {
                    super.replace(fb, offset, length, text, attrs)
                }
            }

            override fun remove(fb: FilterBypass, offset: Int, length: Int) {
                val currentText = fb.document.getText(0, fb.document.length)
                val newText = currentText.substring(0, offset) + currentText.substring(offset + length)
                if (isValidInput(newText)) {
                    super.remove(fb, offset, length)
                }
            }

            private fun isValidInput(text: String): Boolean {
                if (text.isBlank()) {
                    return true
                }
                return ValidationUtils.isValidNumber(text, allowZero)
            }
        }

        document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) = updateButtonState()
            override fun removeUpdate(e: DocumentEvent?) = updateButtonState()
            override fun changedUpdate(e: DocumentEvent?) = updateButtonState()
        })
    }

    fun isValidInput(): Boolean {
        return ValidationUtils.isValidNumber(text, allowZero)
    }

}