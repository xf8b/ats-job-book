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
    private lateinit var startingState: String
    private lateinit var startingCity: String
    private lateinit var endingState: String
    private lateinit var endingCity: String
    private lateinit var startingCompany: String
    private lateinit var endingCompany: String
    private lateinit var loadType: String

    var startingCitiesAvailable = listOf<String>()
        private set
    var endingCitiesAvailable = listOf<String>()
        private set

    fun onStartingStateChange(state: String) {
        startingState = state
        startingCitiesAvailable = Resources.loadCities(state)

        LOGGER.info("Starting state was changed to $startingState")
        LOGGER.info("Starting cities available are now ${startingCitiesAvailable.joinToString()}")

        // publish event to notify the view-model
        eventBus.publish(StartJobEventType.CHANGE_STARTING_CITIES_AVAILABLE)
    }

    fun onStartingCityChange(city: String) {
        startingCity = city

        LOGGER.info("Starting city was changed to $startingCity, $startingState")
    }

    fun onEndingStateChange(state: String) {
        endingState = state
        endingCitiesAvailable = Resources.loadCities(state)

        LOGGER.info("Ending state was changed to $endingState")
        LOGGER.info("Ending cities available are now ${endingCitiesAvailable.joinToString()}")

        eventBus.publish(StartJobEventType.CHANGE_ENDING_CITIES_AVAILABLE)
    }

    fun onEndingCityChange(city: String) {
        endingCity = city

        LOGGER.info("Ending city was changed to $endingCity, $endingState")
    }

    fun onStartingCompanyChange(company: String) {
        startingCompany = company

        LOGGER.info("Starting company was changed to $startingCompany")
    }

    fun onEndingCompanyChange(company: String) {
        endingCompany = company

        LOGGER.info("Ending company was changed to $endingCompany")
    }

    fun onLoadTypeChange(loadType: String) {
        this.loadType = loadType

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
    CHANGE_ENDING_CITIES_AVAILABLE,
}