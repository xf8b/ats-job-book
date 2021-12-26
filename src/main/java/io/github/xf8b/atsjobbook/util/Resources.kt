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

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.net.URL

/**
 * A utility class which helps with finding and loading resources.
 */
class Resources {
    companion object {
        private val LOGGER by LoggerDelegate()

        /**
         * Gets and returns the [URL] of a file in the resources.
         *
         * @return the [URL] of the file, or null if the file does not exist
         */
        private fun resourceUrl(name: String): URL? = Resources::class.java.getResource(name)

        /**
         * Gets and returns the [InputStream] of a file in the resources.
         *
         * @return the [InputStream] of the file, or null if the file does not exist
         */
        private fun resourceStream(name: String): InputStream? = Resources::class.java.getResourceAsStream(name)

        /**
         * Loads and returns the contents of a file in the resources.
         *
         * @return a [String] containing the file's contents
         * @throws NoSuchElementException if there is no such file with the specified name
         */
        private fun loadFile(name: String): String = try {
            val stream = resourceStream(name) ?: throw NoSuchElementException("No such file with name $name")

            stream.bufferedReader().use(Reader::readText)
        } catch (exception: Exception) {
            throw exception.also {
                LOGGER.error("An error occurred while trying to load $name", exception)
            }
        }

        /**
         * Loads a FXML file and returns the loaded object's hierarchy.
         *
         * @return the loaded object's hierarchy
         * @throws NoSuchElementException if there is no such file with the specified name
         * @throws IOException if an error occurs during loading (and the file exists)
         */
        fun loadFxml(name: String): Parent = try {
            FXMLLoader.load(
                resourceUrl("/io/github/xf8b/atsjobbook/view/$name")
                    ?: throw NoSuchElementException("No such file with name $name")
            )
        } catch (exception: Exception) {
            throw exception.also { LOGGER.error("Could not load FXML file $name", exception) }
        }

        /**
         * Load all the states from `places.json` (the key values).
         *
         * @return a [List] containing all the states
         */
        fun loadStates() = JsonParser.parseString(loadFile("places.json")) // load file and parse
            .asJsonObject
            .keySet() // get all the keys
            .toList() // turn into list
            .sorted() // sort alphabetically

        /**
         * Load all the cities from `places.json`, given a specific state.
         *
         * @return a [List] containing all the cities of the given state
         */
        fun loadCities(state: String) = JsonParser.parseString(loadFile("places.json")) // load file and parse
            .asJsonObject
            .get(state) // get the given state's cities
            .asJsonArray
            .toList() // turn into list
            .map(JsonElement::getAsString) // convert into strings
            .sorted() // sort alphabetically

        /**
         * Load all the companies from `companies.json`.
         *
         * @return a [List] containing all the companies
         */
        fun loadCompanies() = JsonParser.parseString(loadFile("companies.json")) // load file and parse
            .asJsonArray
            .toList() // turn into list
            .map(JsonElement::getAsString) // convert into strings
            .sorted() // sort alphabetically
    }
}