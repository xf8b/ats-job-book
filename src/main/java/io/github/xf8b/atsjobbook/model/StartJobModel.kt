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

    fun onStartingStateChange(state: String) {
        // change starting cities available to the state's cities
        startingCitiesAvailable = Resources.loadCities(state)

        LOGGER.info("Starting state was changed to $state")
        LOGGER.info("Starting cities available are now ${startingCitiesAvailable.joinToString()}")

        // publish event to notify the view model
        eventBus.publish(StartJobEventType.CHANGE_STARTING_CITIES_AVAILABLE)
    }

    fun onStartingCityChange(city: String?) {
        // prevent null being used as the starting city during a state change
        if (city == null) return

        LOGGER.info("Starting city was changed to $city")
    }

    fun onEndingStateChange(state: String) {
        // change ending cities available to the state's cities
        endingCitiesAvailable = Resources.loadCities(state)

        LOGGER.info("Ending state was changed to $state")
        LOGGER.info("Ending cities available are now ${endingCitiesAvailable.joinToString()}")

        // publish event to notify the view model
        eventBus.publish(StartJobEventType.CHANGE_ENDING_CITIES_AVAILABLE)
    }

    fun onEndingCityChange(city: String?) {
        // prevent null being used as the ending city during a state change
        if (city == null) return

        LOGGER.info("Ending city was changed to $city")
    }

    fun onStartingCompanyChange(company: String) {
        LOGGER.info("Starting company was changed to $company")
    }

    fun onEndingCompanyChange(company: String) {
        LOGGER.info("Ending company was changed to $company")
    }

    fun onLoadTypeChange(loadType: String) {
        LOGGER.info("Load type was set to $loadType")
    }

    fun onLoadWeightChange(loadWeight: String) {
        LOGGER.info("Load weight was set to $loadWeight")
    }

    fun onLoadWeightMeasurementChange(measurement: String) {
        LOGGER.info("Load weight is now measured in $measurement")
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