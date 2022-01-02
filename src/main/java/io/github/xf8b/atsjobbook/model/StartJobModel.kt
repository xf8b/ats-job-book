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

package io.github.xf8b.atsjobbook.model

import com.google.gson.GsonBuilder
import io.github.xf8b.atsjobbook.util.EventBus
import io.github.xf8b.atsjobbook.util.EventType
import io.github.xf8b.atsjobbook.util.LoggerDelegate
import io.github.xf8b.atsjobbook.util.Resources
import java.nio.file.Files
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class StartJobModel {
    val eventBus = EventBus()
    var startingCitiesAvailable = listOf<String>()
        private set
    var endingCitiesAvailable = listOf<String>()
        private set

    fun updateStartingCitiesAvailable(state: String) {
        // change starting cities available to the state's cities
        startingCitiesAvailable = Resources.STATES_TO_CITIES[state]
            ?: throw IllegalStateException("State could not be found in places.json")

        LOGGER.info("Updated starting cities available to ${startingCitiesAvailable.joinToString()}")

        // notify the view model
        eventBus.publish(StartJobEventType.STARTING_CITIES_AVAILABLE_UPDATED)
    }

    fun updateEndingCitiesAvailable(state: String) {
        // change ending cities available to the state's cities
        endingCitiesAvailable = Resources.STATES_TO_CITIES[state]
            ?: throw IllegalStateException("State could not be found in places.json")

        LOGGER.info("Updated ending cities available to ${endingCitiesAvailable.joinToString()}")

        // notify the view model
        eventBus.publish(StartJobEventType.ENDING_CITIES_AVAILABLE_UPDATED)
    }

    fun save(data: StartJobData) {
        LOGGER.info("Saving a job with data $data")

        try {
            // get current date in ISO 8601 basic format
            val date = DATE_TIME_FORMATTER.format(Instant.now())
            val filePath = Resources.userDirPath("storage/job-$date.json")

            if (Files.notExists(filePath)) {
                // create file if it doesn't exist (which it shouldn't)
                Files.createFile(filePath)

                LOGGER.info("Created file in location $filePath")
            }

            // write the json to the file
            Files.writeString(filePath, GSON.toJson(data))

            // notify the view model
            eventBus.publish(StartJobEventType.SAVE_COMPLETED)
        } catch (exception: Exception) {
            // log the error
            LOGGER.error("Could not save job", exception)

            // notify the view model
            eventBus.publish(StartJobEventType.SAVE_ERROR)
        }
    }

    companion object {
        private val LOGGER by LoggerDelegate()
        private val DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("uuuuMMdd'T'HHmmssXX")
            .withZone(ZoneOffset.UTC)
        private val GSON = GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
}

enum class StartJobEventType : EventType {
    /**
     * Fired when the available starting city choices have been updated.
     */
    STARTING_CITIES_AVAILABLE_UPDATED,

    /**
     * Fired when the available ending city choices have been updated.
     */
    ENDING_CITIES_AVAILABLE_UPDATED,

    /**
     * Fired when the save is completed.
     */
    SAVE_COMPLETED,

    /**
     * Fired when the save could not be completed.
     */
    SAVE_ERROR,
}

data class StartJobData(
    val startingState: String,
    val startingCity: String,
    val endingState: String,
    val endingCity: String,
    val startingCompany: String,
    val endingCompany: String,
    val loadType: String,
    val loadWeight: Int,
    val loadWeightMeasurement: String,
    val dayOfWeek: String,
    val hour: Int,
    val minute: Int
)