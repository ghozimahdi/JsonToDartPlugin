package com.ghozimahdi.jsontodart.ui

import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import javax.swing.*

class DashboardPage(private val selectedDirectory: VirtualFile?) {
    private val jsonInputArea = JTextArea(10, 50)
    private val classNameField = JTextField("GeneratedClass", 20)
    private val outputArea = JTextArea(10, 50).apply { isEditable = false }

    private fun saveDartFile(dartCode: String, className: String, directory: VirtualFile) {
        val targetPath = "${directory.path}/$className.dart"

        val file = File(targetPath)
        file.writeText(dartCode)

        val virtualFile: VirtualFile? = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file)
        virtualFile?.let { VirtualFileManager.getInstance().syncRefresh() }
    }

    fun doOKAction() {
        val jsonText = jsonInputArea.text.trim()
        val className = classNameField.text.trim()
        if (jsonText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "JSON cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }

        try {
            val jsonElement = com.google.gson.JsonParser.parseString(jsonText)
            val dartCode = JsonToDartConverter.generateDartClass(jsonElement, className)
            outputArea.text = dartCode

            selectedDirectory?.let { saveDartFile(dartCode, className, it) }

            JOptionPane.showMessageDialog(
                null,
                "File created successfully: ${selectedDirectory?.path}/$className.dart",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            )
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(null, "Invalid JSON format!", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    fun render(): JPanel {
        val panel = JPanel(BorderLayout())

        // JSON Input
        val inputPanel = JPanel(BorderLayout())
        inputPanel.add(JLabel("Paste JSON Here:"), BorderLayout.NORTH)
        inputPanel.add(JScrollPane(jsonInputArea), BorderLayout.CENTER)

        // Class Name Input
        val namePanel = JPanel(BorderLayout())
        namePanel.add(JLabel("Class Name:"), BorderLayout.WEST)
        namePanel.add(classNameField, BorderLayout.CENTER)

        // Output Area
        val outputPanel = JPanel(BorderLayout())
        outputPanel.add(JLabel("Generated Dart Code:"), BorderLayout.NORTH)
        outputPanel.add(JScrollPane(outputArea), BorderLayout.CENTER)

        panel.add(inputPanel, BorderLayout.NORTH)
        panel.add(namePanel, BorderLayout.CENTER)
        panel.add(outputPanel, BorderLayout.SOUTH)
        panel.preferredSize = Dimension(600, 400)

        return panel
    }
}