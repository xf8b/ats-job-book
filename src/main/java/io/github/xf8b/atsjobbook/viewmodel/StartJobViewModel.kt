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

package io.github.xf8b.atsjobbook.viewmodel

import io.github.xf8b.atsjobbook.model.StartJobEventType
import io.github.xf8b.atsjobbook.model.StartJobModel
import io.github.xf8b.atsjobbook.util.EventBus
import io.github.xf8b.atsjobbook.util.LoggerDelegate
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList

class StartJobViewModel {
    private val eventBus = EventBus()
    private val model = StartJobModel(eventBus)
    val selectedStateProperty = SimpleObjectProperty<String>()
    val selectedCityProperty = SimpleObjectProperty<String>()
    val cityChoicesProperty = SimpleObjectProperty<ObservableList<String>>()

    init {
        eventBus.subscribe(StartJobEventType.CHANGE_CITIES_AVAILABLE) {
            cityChoicesProperty.get().setAll(model.citiesAvailable)
        }
    }

    fun onStateChange() {
        val state = selectedStateProperty.get()

        model.onStateChange(state)
        LOGGER.info("You selected: $state")
    }

    fun onCityChange() {
        LOGGER.info("You selected: ${selectedCityProperty.get()}, ${selectedStateProperty.get()}")
    }

    companion object {
        private val LOGGER by LoggerDelegate()
    }
}