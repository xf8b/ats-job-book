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

package io.github.xf8b.atsjobbook.utility

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.github.xf8b.atsjobbook.Main
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

private val LOGGER = LoggerFactory.getLogger("io.github.xf8b.atsjobbook.utility.Resources")
val STATES_TO_CITIES by lazy {
    try {
        return@lazy JsonParser.parseString(loadFile("utility/places.json")) // load file and parse
            .asJsonObject.entrySet().associate { entry -> entry.key to entry.value } // turn into a map
            .mapValues { (_, value) ->
                value.asJsonArray.toList().map(JsonElement::getAsString)
                    .sorted() // turn the value into a sorted list of strings
            }
    } catch (exception: Exception) {
        throw IOException("Could not load utility/places.json", exception)
    }
}
val STATES get() = STATES_TO_CITIES.keys.toList()
val COMPANIES by lazy {
    try {
        return@lazy JsonParser.parseString(loadFile("utility/companies.json")) // load file and parse
            .asJsonArray.toList().map(JsonElement::getAsString).sorted() // turn into a sorted list of strings
    } catch (exception: Exception) {
        throw IOException("Could not load utility/companies.json", exception)
    }
}
val PROPERTIES by lazy {
    try {
        val defaultProperties = Properties().apply {
            resourceStream("utility/default-settings.properties").use(this::load)
        } // get and load default properties
        val properties = Properties(defaultProperties)
        val propertiesPath = userDirectoryPath("storage/settings.properties")

        if (Files.notExists(propertiesPath)) {
            // create a property file if it doesn't exist from the default properties file
            resourceStream("utility/default-settings.properties").use { Files.copy(it, propertiesPath) }

            LOGGER.info("Copied utility/default-settings.properties to $propertiesPath")
        }

        Files.newBufferedReader(propertiesPath).use(properties::load) // read the properties file

        return@lazy properties
    } catch (exception: Exception) {
        throw IOException("Could not load settings.properties", exception)
    }
}

/**
 * Gets and returns the [Path] of a file or folder in `user.dir`.
 *
 * @param name the name of the file or folder to get the [Path] of
 * @return the [Path] of the file or folder
 */
fun userDirectoryPath(name: String): Path = Path.of(System.getProperty("user.dir")).resolve(name)

/**
 * Gets and returns the [InputStream] of a file in `resources/io/github/xf8b/atsjobbook/
 *
 * @param name the name of the file to get the [InputStream] of
 * @return the [InputStream] of the file
 * @throws IOException if the file does not exist
 */
private fun resourceStream(name: String): InputStream = Main::class.java.getResourceAsStream(name)
    ?: throw IOException("""File "$name" does not exist in resources/io/github/xf8b/atsjobbook/""")

/**
 * Loads and returns the contents of a file in `resources/io/github/xf8b/atsjobbook/`.
 *
 * @param name the name of the file to load
 * @return a [String] containing the file's contents
 * @throws IOException if the file could not be loaded (which can be caused by the file not existing)
 */
private fun loadFile(name: String): String = try {
    resourceStream(name).bufferedReader().use(Reader::readText)
} catch (exception: Exception) {
    throw IOException("Could not load $name", exception)
}

/**
 * Loads a FXML file and returns the loaded object's hierarchy.
 *
 * @param name the name of the FXML file to load
 * @param windowFactory the window factory to use if there is an error
 * @return the loaded object's hierarchy
 * @throws IOException if the file could not be loaded (which can be caused by the file not existing)
 */
fun loadFxml(name: String, windowFactory: WindowFactory): Parent = try {
    val loader = FXMLLoader(Main::class.java.getResource("view/"), RESOURCE_BUNDLE)

    resourceStream("view/$name").use(loader::load)
} catch (exception: Exception) {
    windowFactory.createErrorAlert("error.loading.header", "error.loading.content")
    throw IOException("Could not load FXML $name", exception)
}

fun createFileIfNotExists(path: Path) {
    if (Files.notExists(path)) {
        Files.createFile(path)

        LOGGER.info("Created file in $path")
    }
}