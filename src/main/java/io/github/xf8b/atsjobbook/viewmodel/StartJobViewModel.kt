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
import io.github.xf8b.atsjobbook.util.StandardEventType
import io.github.xf8b.atsjobbook.util.WindowFactory
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList

class StartJobViewModel(private val windowFactory: WindowFactory) {
    private val model = StartJobModel()
    val eventBus = EventBus()

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

    // date and time properties
    val dayOfWeekProperty = SimpleStringProperty()
    val hourProperty = SimpleStringProperty()
    val minuteProperty = SimpleStringProperty()

    init {
        model.eventBus.subscribe(StartJobEventType.STARTING_CITIES_AVAILABLE_UPDATED) {
            // reset the value property
            startingCityProperty.set(null)
            // change the starting cities available to the new ones
            startingCitiesAvailableProperty.get().setAll(model.startingCitiesAvailable)
        }

        model.eventBus.subscribe(StartJobEventType.ENDING_CITIES_AVAILABLE_UPDATED) {
            // reset the value property
            endingCityProperty.set(null)
            // change the ending cities available to the new ones
            endingCitiesAvailableProperty.get().setAll(model.endingCitiesAvailable)
        }

        model.eventBus.subscribe(StartJobEventType.SAVE_COMPLETED) {
            eventBus.publish(StandardEventType.CLOSE_WINDOW)
        }

        model.eventBus.subscribe(StartJobEventType.SAVE_ERROR) {
            windowFactory.createErrorAlert(
                title = "An error occurred during saving",
                content = "Please report this bug to the maintainers and include the log file."
            )
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
        val dayOfWeek = dayOfWeekProperty.get()
        val hour = hourProperty.get()
        val minute = minuteProperty.get()

        if (startingState == null
            || startingCity == null
            || endingState == null
            || endingCity == null
            || startingCompany == null
            || endingCompany == null
            || loadType.isNullOrBlank()
            || loadWeight.isNullOrBlank()
            || loadWeightMeasurement == null
            || dayOfWeek == null
            || hour.isNullOrBlank()
            || minute.isNullOrBlank()
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

        val hourAsInt = hour.toIntOrNull()

        if (hourAsInt == null) {
            windowFactory.createErrorAlert(
                title = "Hour is invalid",
                content = "The hour must be an integer."
            )

            return
        } else if (hourAsInt !in 0..23) {
            windowFactory.createErrorAlert(
                title = "Hour is invalid",
                content = "The hour must be between 0 and 23 (inclusive)."
            )

            return
        }

        val minuteAsInt = minute.toIntOrNull()

        if (minuteAsInt == null) {
            windowFactory.createErrorAlert(
                title = "Minute is invalid",
                content = "The minute must be an integer."
            )

            return
        } else if (minuteAsInt !in 0..59) {
            windowFactory.createErrorAlert(
                title = "Minute is invalid",
                content = "The minute must be between 0 and 59 (inclusive)."
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
                loadWeightMeasurement,
                dayOfWeek,
                hourAsInt,
                minuteAsInt
            )
        )
    }
}