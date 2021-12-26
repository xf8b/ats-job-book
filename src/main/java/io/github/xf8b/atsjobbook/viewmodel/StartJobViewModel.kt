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
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList

class StartJobViewModel {
    private val eventBus = EventBus()
    private val model = StartJobModel(eventBus)

    // starting city properties
    val startingStateProperty = SimpleStringProperty()
    val startingCityProperty = SimpleStringProperty()
    val startingCityChoicesProperty = SimpleObjectProperty<ObservableList<String>>()

    // ending city properties
    val endingStateProperty = SimpleStringProperty()
    val endingCityProperty = SimpleStringProperty()
    val endingCityChoicesProperty = SimpleObjectProperty<ObservableList<String>>()

    // company properties
    val startingCompanyProperty = SimpleStringProperty()
    val endingCompanyProperty = SimpleStringProperty()

    // load properties
    val loadTypeProperty = SimpleStringProperty()
    val loadWeightProperty = SimpleStringProperty()
    val loadWeightMeasurementProperty = SimpleStringProperty()

    init {
        eventBus.subscribe(StartJobEventType.CHANGE_STARTING_CITIES_AVAILABLE) {
            // reset the value property
            startingCityProperty.set(null)
            // replace all the old starting cities to the new ones
            startingCityChoicesProperty.get().setAll(model.startingCitiesAvailable)
        }

        eventBus.subscribe(StartJobEventType.CHANGE_ENDING_CITIES_AVAILABLE) {
            // reset the value property
            endingCityProperty.set(null)
            // replace all the old ending cities to the new ones
            endingCityChoicesProperty.get().setAll(model.endingCitiesAvailable)
        }
    }

    fun onStartingStateChange() {
        model.onStartingStateChange(startingStateProperty.get())
    }

    fun onStartingCityChange() {
        model.onStartingCityChange(startingCityProperty.get())
    }

    fun onEndingStateChange() {
        model.onEndingStateChange(endingStateProperty.get())
    }

    fun onEndingCityChange() {
        model.onEndingCityChange(endingCityProperty.get())
    }

    fun onStartingCompanyChange() {
        model.onStartingCompanyChange(startingCompanyProperty.get())
    }

    fun onEndingCompanyChange() {
        model.onEndingCompanyChange(endingCompanyProperty.get())
    }

    fun onLoadTypeChange() {
        model.onLoadTypeChange(loadTypeProperty.get())
    }

    fun onLoadWeightChange() {
        model.onLoadWeightChange(loadWeightProperty.get())
    }

    fun onLoadWeightMeasurementChange() {
        model.onLoadWeightMeasurementChange(loadWeightMeasurementProperty.get())
    }
}