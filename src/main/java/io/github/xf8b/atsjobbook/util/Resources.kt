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

import io.github.xf8b.atsjobbook.Main
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.io.IOException
import java.net.URL

/**
 * A utility class which helps with finding and loading resources.
 */
class Resources {
    companion object {
        private val LOGGER by LoggerDelegate()

        /**
         * Gets and returns the URL of a file in the JAR (?).
         *
         * @return the URL of the file, or null if it does not exist
         */
        private fun resourceUrl(name: String): URL? = Main::class.java.classLoader.getResource(name)

        /**
         * Loads a FXML file and returns the loaded object's hierarchy.
         *
         * @return the loaded object's hierarchy
         * @throws NoSuchElementException if there is no such file with the specified name
         * @throws IOException if an error occurs during loading (and the file exists)
         */
        fun loadFxml(name: String): Parent = try {
            FXMLLoader.load(
                resourceUrl(name) ?: throw NoSuchElementException("No such file with name $name")
            )
        } catch (exception: Exception) {
            throw exception.also { LOGGER.error("Could not load FXML file $name", exception) }
        }
    }
}