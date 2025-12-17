package com.github.djrasmusp.oklchconverter.toolWindow

import com.github.djrasmusp.oklchconverter.MyBundle
import com.github.djrasmusp.oklchconverter.services.ColorConversionService
import com.intellij.openapi.components.service
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.FormBuilder
import java.awt.datatransfer.StringSelection
import javax.swing.JButton
import javax.swing.JPanel


class MyToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<ColorConversionService>()

        fun getContent(): JPanel {
            val input = JBTextField()
            val output = JBTextField().apply { isEditable = false }
            val errorLabel = JBLabel().apply { foreground = JBColor.RED }
            val copyButton = JButton(MyBundle.message("copy")).apply { isEnabled = false }

            fun convertAndRender() {
                val text = input.text
                val result = runCatching { service.convert(text) }
                result.onSuccess {
                    output.text = it
                    errorLabel.text = ""
                    copyButton.isEnabled = true
                }.onFailure { throwable ->
                    output.text = ""
                    copyButton.isEnabled = false
                    errorLabel.text = throwable.message ?: MyBundle.message("errorUnknown")
                }
            }

            val convertButton = JButton(MyBundle.message("convert")).apply {
                addActionListener { convertAndRender() }
            }

            input.addActionListener { convertAndRender() }

            copyButton.addActionListener {
                CopyPasteManager.getInstance().setContents(StringSelection(output.text))
                errorLabel.text = MyBundle.message("copied")
            }

            val panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel(MyBundle.message("inputLabel")), input)
                .addComponent(
                    JBPanel<JBPanel<*>>().apply {
                        add(convertButton)
                        add(copyButton)
                    }
                )
                .addLabeledComponent(JBLabel(MyBundle.message("outputLabel")), output)
                .addComponent(errorLabel)
                .panel

            return JBPanel<JBPanel<*>>().apply { add(panel) }
        }
    }
}
