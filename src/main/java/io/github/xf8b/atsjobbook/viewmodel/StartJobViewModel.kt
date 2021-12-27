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

import io.github.xf8b.atsjobbook.model.StartJobData
import io.github.xf8b.atsjobbook.model.StartJobEventType
import io.github.xf8b.atsjobbook.model.StartJobModel
import io.github.xf8b.atsjobbook.util.EventBus
import io.github.xf8b.atsjobbook.util.WindowFactory
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList

class StartJobViewModel(private val windowFactory: WindowFactory) {
    private val eventBus = EventBus()
    private val model = StartJobModel(eventBus)

    // starting city properties
    val startingStateProperty = SimpleStringProperty()
    val startingCityProperty = SimpleStringProperty()
    val startingCitiesAvailableProperty = SimpleObjectProperty<ObservableList<String>>()

    // ending city properties
    val endingStateProperty = SimpleStringProperty()
    val endingCityProperty = SimpleStringProperty()
    val endingCitiesAvailableProperty = SimpleObjectProperty<ObservableList<String>>()

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
            // change the starting cities available to the new ones
            startingCitiesAvailableProperty.get().setAll(model.startingCitiesAvailable)
        }

        eventBus.subscribe(StartJobEventType.CHANGE_ENDING_CITIES_AVAILABLE) {
            // reset the value property
            endingCityProperty.set(null)
            // change the ending cities available to the new ones
            endingCitiesAvailableProperty.get().setAll(model.endingCitiesAvailable)
        }
    }

    fun onStartingStateChange() {
        model.updateStartingCitiesAvailable(startingStateProperty.get())
    }

    fun onEndingStateChange() {
        model.updateEndingCitiesAvailable(endingStateProperty.get())
    }

    fun onSaveButtonPressed() {
        val startingState = startingStateProperty.get()
        val startingCity = startingCityProperty.get()
        val endingState = endingStateProperty.get()
        val endingCity = endingCityProperty.get()
        val startingCompany = startingCompanyProperty.get()
        val endingCompany = endingCompanyProperty.get()
        val loadType = loadTypeProperty.get()
        val loadWeight = loadWeightProperty.get()
        val loadWeightMeasurement = loadWeightMeasurementProperty.get()

        if (startingState == null
            || startingCity == null
            || endingState == null
            || endingCity == null
            || startingCompany == null
            || endingCompany == null
            || loadType == null || loadType.isBlank()
            || loadWeight == null || loadWeight.isBlank()
            || loadWeightMeasurement == null
        ) {
            windowFactory.createErrorAlert(
                title = "Input is not complete",
                content = "Please fill out all fields before saving."
            )

            return
        }

        val loadWeightAsInt = loadWeight.toIntOrNull()

        if (loadWeightAsInt == null) {
            windowFactory.createErrorAlert(
                title = "Load weight is invalid",
                content = "The load weight must be an integer."
            )

            return
        } else if (loadWeightAsInt <= 0) {
            windowFactory.createErrorAlert(
                title = "Load weight is invalid",
                content = "The load weight must be greater than 0."
            )

            return
        }

        model.save(
            StartJobData(
                startingState,
                startingCity,
                endingState,
                endingCity,
                startingCompany,
                endingCompany,
                loadType,
                loadWeightAsInt,
                loadWeightMeasurement
            )
        )
    }
}