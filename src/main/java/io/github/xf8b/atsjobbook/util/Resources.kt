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

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * A utility class which helps with finding and loading resources.
 */
class Resources {
    companion object {
        private val LOGGER by LoggerDelegate()
        val STATES get() = STATES_TO_CITIES.keys.toList()
        val STATES_TO_CITIES by lazy(this::loadStatesToCities)
        val COMPANIES by lazy(this::loadCompanies)
        val PROPERTIES by lazy(this::loadProperties)

        /**
         * Gets and returns the [Path] of a file or folder in the user directory.
         *
         * @return the [Path] of the file or folder
         */
        fun userDirPath(name: String): Path = Path.of(System.getProperty("user.dir")).resolve(name)

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
            resourceStream(name)
                ?.use { it.bufferedReader().use(Reader::readText) }
                ?: throw NoSuchElementException("No such file with name $name")
        } catch (exception: Exception) {
            LOGGER.error("Could not load $name", exception)

            throw RuntimeException(exception)
        }

        /**
         * Loads a FXML file and returns the loaded object's hierarchy.
         *
         * @return the loaded object's hierarchy
         * @throws NoSuchElementException if there is no such file with the specified name
         * @throws IOException if an error occurs during loading (and the file exists)
         */
        fun loadFxml(name: String): Parent = try {
            val loader = FXMLLoader().apply {
                resources = ResourceBundle.getBundle(
                    "io.github.xf8b.atsjobbook.i18n.atsjobbook",
                    I18n.LOCALE
                )
            }

            resourceStream("/io/github/xf8b/atsjobbook/view/$name")
                ?.use(loader::load)
                ?: throw NoSuchElementException("No such file with name $name")
        } catch (exception: Exception) {
            LOGGER.error("Could not load FXML $name", exception)

            throw RuntimeException(exception)
        }

        private fun loadProperties(): Properties {
            try {
                val defaultProperties = Properties()

                resourceStream("default-settings.properties")
                    ?.use(defaultProperties::load)
                    ?: throw IllegalStateException("default-settings.properties does not exist")

                val properties = Properties(defaultProperties)
                val propertiesPath = userDirPath("storage/settings.properties")

                if (Files.notExists(propertiesPath)) {
                    resourceStream("default-settings.properties")
                        ?.use { stream -> Files.copy(stream, propertiesPath) }
                        ?: throw IllegalStateException("default-settings.properties does not exist")

                    LOGGER.info("Created file in location $propertiesPath")
                }

                properties.load(Files.newBufferedReader(propertiesPath))

                return properties
            } catch (exception: Exception) {
                LOGGER.error("Could not load settings file", exception)

                throw RuntimeException(exception)
            }
        }

        /**
         * Load all the states and cities from `places.json`.
         *
         * @return a [Map] containing all the states and cities
         */
        private fun loadStatesToCities(): Map<String, List<String>> = try {
            JsonParser.parseString(loadFile("places.json")) // load file and parse
                .asJsonObject.entrySet().associate { entry -> entry.key to entry.value }.mapValues { (_, value) ->
                    value.asJsonArray.toList().map(JsonElement::getAsString).sorted()
                }
        } catch (exception: Exception) {
            LOGGER.error("Could not load places.json", exception)

            throw RuntimeException(exception)
        }

        /**
         * Load all the companies from `companies.json`.
         *
         * @return a [List] containing all the companies
         */
        private fun loadCompanies(): List<String> = try {
            JsonParser.parseString(loadFile("companies.json")) // load file and parse
                .asJsonArray.toList() // turn into list
                .map(JsonElement::getAsString) // convert into strings
                .sorted() // sort alphabetically
        } catch (exception: Exception) {
            LOGGER.error("Could not load companies.json", exception)

            throw RuntimeException(exception)
        }
    }
}