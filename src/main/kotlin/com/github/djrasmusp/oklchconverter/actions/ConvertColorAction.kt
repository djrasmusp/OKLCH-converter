package com.github.djrasmusp.oklchconverter.actions

import com.github.djrasmusp.oklchconverter.MyBundle
import com.github.djrasmusp.oklchconverter.services.ColorConversionService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

class ConvertColorAction : AnAction(
    MyBundle.message("action.convert.text"),
    MyBundle.message("action.convert.description"),
    null
) {

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val hasSelection = editor?.selectionModel?.hasSelection() == true
        e.presentation.isEnabledAndVisible = e.project != null && hasSelection
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return

        val selectionModel = editor.selectionModel
        val selectedText = selectionModel.selectedText ?: return

        val service = project.service<ColorConversionService>()
        val conversionResult = service.convertFile(selectedText)

        if (conversionResult.errors.isNotEmpty()) {
            val errorMessage = conversionResult.errors.joinToString("\n")
            notify(project, MyBundle.message("action.convert.errors", conversionResult.errors.size, errorMessage), NotificationType.WARNING)
        }

        if (conversionResult.replacements.isEmpty()) {
            notify(project, MyBundle.message("action.convert.noColors"), NotificationType.INFORMATION)
            return
        }

        replaceSelection(project, editor, conversionResult.convertedContent)
        
        // Keep selection after conversion
        val newLength = conversionResult.convertedContent.length
        selectionModel.setSelection(
            selectionModel.selectionStart,
            selectionModel.selectionStart + newLength
        )

        val message = MyBundle.message("action.convert.successMultiple", conversionResult.replacements.size)
        notify(project, message, NotificationType.INFORMATION)
    }

    private fun replaceSelection(project: Project, editor: Editor, replacement: String) {
        val selectionModel = editor.selectionModel
        val start = selectionModel.selectionStart
        val end = selectionModel.selectionEnd
        WriteCommandAction.runWriteCommandAction(project) {
            editor.document.replaceString(start, end, replacement)
        }
    }

    private fun notify(project: Project, message: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("OKLCH Converter")
            .createNotification(message, type)
            .notify(project)
    }
}

