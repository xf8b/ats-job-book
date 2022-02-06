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

package io.github.xf8b.atsjobbook

import io.github.xf8b.atsjobbook.utility.LoggerDelegate
import io.github.xf8b.atsjobbook.utility.getLocalizedString
import io.github.xf8b.atsjobbook.utility.userDirectoryPath
import io.github.xf8b.atsjobbook.view.MainView
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import java.nio.file.Files

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val storagePath = userDirectoryPath("storage")

        if (Files.notExists(storagePath)) {
            // create the storage folder if it doesn't exist
            Files.createDirectories(storagePath)

            LOGGER.info("Created directory in location $storagePath")
        }

        // set up the main screen
        primaryStage.title = getLocalizedString("window.main.title")
        primaryStage.scene = Scene(MainView().root)
        primaryStage.show()

        LOGGER.info("Successfully started the program!")
    }

    companion object {
        private val LOGGER by LoggerDelegate()

        @JvmStatic
        fun main(vararg args: String) {
            launch(Main::class.java, *args)
        }
    }
}