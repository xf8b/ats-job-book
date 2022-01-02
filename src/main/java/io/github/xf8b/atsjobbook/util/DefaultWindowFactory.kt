/*
 * Copyright (c) 2021-2022 xf8b.
 *
 * This file is part of ats-job-book.
 *
 * ats-job-book is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ats-job-book is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ats-job-book. If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.xf8b.atsjobbook.util

import io.github.xf8b.atsjobbook.view.StartJobView
import io.github.xf8b.atsjobbook.view.View
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Stage

class DefaultWindowFactory : WindowFactory {
    private fun createWindow(view: View, title: String) {
        // create the window
        Stage().apply {
            this.title = title // set the title
            this.scene = Scene(view.root) // use the view's fxml file for the scene
        }.show() // show the window

        LOGGER.info("Opened a new window with the title $title")
    }

    override fun createStartJobWindow() {
        createWindow(StartJobView(), I18n.getString("window.start_job.title"))
    }

    override fun createErrorAlert(headerKey: String, contentKey: String) {
        val header = I18n.getString(headerKey)
        val content = I18n.getString(contentKey)

        // create the alert
        Alert(Alert.AlertType.ERROR, content, ButtonType.CLOSE).apply {
            title = I18n.getString("window.error.title") // set the title (not to be confused with the header)
            headerText = header // set the header
        }.show() // show the alert

        LOGGER.info("Opened a new error alert with the header $header and content $content")
    }

    companion object {
        private val LOGGER by LoggerDelegate()
    }
}