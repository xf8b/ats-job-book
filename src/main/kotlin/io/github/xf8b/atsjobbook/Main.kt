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

package io.github.xf8b.atsjobbook

import io.github.xf8b.atsjobbook.util.LoggerDelegate
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application() {
    override fun start(primaryStage: Stage) {
        // this is all temporary
        val button = Button("Start Job")
        button.setOnAction {
            Stage().apply {
                title = "Start Job"
                val label = Label("Starting City")
                val startingCity = TextField()
                val pane = GridPane().apply {
                    add(label, 0, 0)
                    add(startingCity, 1, 0)
                    hgap = 10.0
                    vgap = 10.0
                }
                val box = VBox(HBox(pane).apply { alignment = Pos.CENTER })
                    .apply { alignment = Pos.CENTER }
                scene = Scene(box, 500.0, 500.0)
            }.show()
        }
        primaryStage.title = "ATS Job Log"
        primaryStage.scene = Scene(StackPane(button), 300.0, 300.0)
        primaryStage.show()

        LOGGER.info("Success!")
    }

    companion object {
        private val LOGGER by LoggerDelegate()

        @JvmStatic
        fun main(vararg args: String) {
            launch(Main::class.java, *args)
        }
    }
}