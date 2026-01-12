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
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class ConvertFileAction : AnAction(
    MyBundle.message("action.convertFile.text"),
    MyBundle.message("action.convertFile.description"),
    null
) {

    override fun update(e: AnActionEvent) {
        val project = e.project
        if (project == null) {
            e.presentation.isEnabledAndVisible = false
            return
        }
        
        // Try to get file from context (Project View) or active editor
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) 
            ?: FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
        
        e.presentation.isEnabledAndVisible = file != null && !file.isDirectory
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        // Try to get file from context (Project View) or active editor
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) 
            ?: FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
            ?: run {
                notify(project, MyBundle.message("error.noFileSelected"), NotificationType.ERROR)
                return
            }

        if (file.isDirectory) {
            notify(project, MyBundle.message("error.fileIsDirectory"), NotificationType.ERROR)
            return
        }

        val document = FileDocumentManager.getInstance().getDocument(file)
        if (document == null) {
            notify(project, MyBundle.message("error.fileRead", "Could not open file document"), NotificationType.ERROR)
            return
        }

        val service = project.service<ColorConversionService>()
        val fileContent = document.text

        val result = service.convertFile(fileContent)

        if (result.errors.isNotEmpty()) {
            val errorMessage = result.errors.joinToString("\n")
            notify(project, MyBundle.message("action.convertFile.errors", result.errors.size, errorMessage), NotificationType.WARNING)
        }

        if (result.replacements.isEmpty()) {
            notify(project, MyBundle.message("action.convertFile.noColors"), NotificationType.INFORMATION)
            return
        }

        WriteCommandAction.runWriteCommandAction(project) {
            document.setText(result.convertedContent)
        }

        val message = MyBundle.message("action.convertFile.success", result.replacements.size)
        notify(project, message, NotificationType.INFORMATION)
    }

    private fun notify(project: Project, message: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("OKLCH Converter")
            .createNotification(message, type)
            .notify(project)
    }
}
