package com.ghozimahdi.jsontodart

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile

class JsonToDartAction : AnAction("Convert JSON to Dart") {
    override fun actionPerformed(event: AnActionEvent) {
        val selectedFile: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if (selectedFile != null && selectedFile.isDirectory) {
            JsonToDartDialog(selectedFile).show()
        } else {
            Messages.showErrorDialog("Please select a valid directory.", "Error")
        }
    }
}