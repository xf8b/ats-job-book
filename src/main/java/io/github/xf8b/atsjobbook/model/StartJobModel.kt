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

package io.github.xf8b.atsjobbook.model

import io.github.xf8b.atsjobbook.util.EventBus
import io.github.xf8b.atsjobbook.util.EventType
import io.github.xf8b.atsjobbook.util.LoggerDelegate
import io.github.xf8b.atsjobbook.util.Resources

class StartJobModel(private val eventBus: EventBus) {
    var startingCitiesAvailable = listOf<String>()
        private set
    var endingCitiesAvailable = listOf<String>()
        private set

    fun updateStartingCitiesAvailable(state: String) {
        // change starting cities available to the state's cities
        startingCitiesAvailable = Resources.loadCities(state)

        LOGGER.info("Starting state was changed to $state")
        LOGGER.info("Starting cities available are now ${startingCitiesAvailable.joinToString()}")

        // publish event to notify the view model
        eventBus.publish(StartJobEventType.CHANGE_STARTING_CITIES_AVAILABLE)
    }

    fun updateEndingCitiesAvailable(state: String) {
        // change ending cities available to the state's cities
        endingCitiesAvailable = Resources.loadCities(state)

        LOGGER.info("Ending state was changed to $state")
        LOGGER.info("Ending cities available are now ${endingCitiesAvailable.joinToString()}")

        // publish event to notify the view model
        eventBus.publish(StartJobEventType.CHANGE_ENDING_CITIES_AVAILABLE)
    }

    fun save(data: StartJobData) {
        // TODO: serialize to the storage folder
    }

    companion object {
        private val LOGGER by LoggerDelegate()
    }
}

enum class StartJobEventType : EventType {
    /**
     * Fired when the available starting city choices have been changed.
     */
    CHANGE_STARTING_CITIES_AVAILABLE,

    /**
     * Fired when the available ending city choices have been changed.
     */
    CHANGE_ENDING_CITIES_AVAILABLE,
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
)