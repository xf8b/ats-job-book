/*
 * Copyright (c) 2021 xf8b.
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

import io.github.xf8b.atsjobbook.view.View
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Stage

class DefaultWindowFactory : WindowFactory {
    override fun createWindow(view: View, title: String) {
        // create the window
        Stage().apply {
            this.title = title // set the title of the window
            this.scene = Scene(view.root) // use the view's fxml file for the scene
        }.show() // show the window

        LOGGER.info("Opened a new window with the title $title")
    }

    override fun createErrorAlert(title: String, content: String) {
        Alert(Alert.AlertType.ERROR, content, ButtonType.CLOSE).apply {
            this.title = title
        }.show()

        LOGGER.info("Opened a new error alert with the title $title and content $content")
    }

    companion object {
        private val LOGGER by LoggerDelegate()
    }
}