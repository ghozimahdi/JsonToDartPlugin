package com.ghozimahdi.jsontodart

import com.ghozimahdi.jsontodart.ui.DashboardPage
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.JComponent

class JsonToDartDialog(private val selectedDirectory: VirtualFile) : DialogWrapper(true) {

    private val dashboardPage: DashboardPage by lazy {
        DashboardPage(selectedDirectory)
    }

    init {
        title = "Convert JSON to Dart"
        init()
    }

    override fun createCenterPanel(): JComponent {
        return dashboardPage.render()
    }

    override fun doOKAction() {
        dashboardPage.doOKAction()
    }
}