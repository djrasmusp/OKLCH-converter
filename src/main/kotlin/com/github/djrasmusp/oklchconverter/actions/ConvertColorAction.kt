package com.github.djrasmusp.oklchconverter.actions

import com.github.djrasmusp.oklchconverter.MyBundle
import com.github.djrasmusp.oklchconverter.services.ColorConversionService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor

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
        val result = runCatching { service.convert(selectedText) }
        result.onSuccess { converted ->
            replaceSelection(editor, converted)
            selectionModel.setSelection(
                selectionModel.selectionStart,
                selectionModel.selectionStart + converted.length
            )
            notify(project, MyBundle.message("action.convert.success", converted), NotificationType.INFORMATION)
        }.onFailure { throwable ->
            notify(project, throwable.message ?: MyBundle.message("errorUnknown"), NotificationType.ERROR)
        }
    }

    private fun replaceSelection(editor: Editor, replacement: String) {
        val selectionModel = editor.selectionModel
        val start = selectionModel.selectionStart
        val end = selectionModel.selectionEnd
        editor.document.replaceString(start, end, replacement)
    }

    private fun notify(project: com.intellij.openapi.project.Project, message: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("OKLCH Converter")
            .createNotification(message, type)
            .notify(project)
    }
}

