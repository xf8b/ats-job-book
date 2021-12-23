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

import io.github.xf8b.atsjobbook.util.CITIES
import io.github.xf8b.atsjobbook.util.EventBus
import io.github.xf8b.atsjobbook.util.EventType
import io.github.xf8b.atsjobbook.util.LoggerDelegate

class StartJobModel(private val eventBus: EventBus) {
    var citiesAvailable = listOf<String>()
        private set

    fun onStateChange(state: String) {
        val cities = CITIES[state]

        citiesAvailable = if (cities == null) {
            LOGGER.error("State not found in the CITIES map; reset the cities list")
            listOf()
        } else {
            cities
        }

        eventBus.publish(StartJobEventType.CHANGE_CITIES_AVAILABLE)
    }

    companion object {
        private val LOGGER by LoggerDelegate()
    }
}

enum class StartJobEventType : EventType {
    CHANGE_CITIES_AVAILABLE,
}