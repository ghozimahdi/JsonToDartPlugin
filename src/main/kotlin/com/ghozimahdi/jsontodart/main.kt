package com.ghozimahdi.jsontodart

import com.ghozimahdi.jsontodart.ui.DashboardPage
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Test UI")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(600, 800)

        val dashboardPage = DashboardPage(null)

        frame.add(dashboardPage.render())
        frame.isVisible = true
    }
}