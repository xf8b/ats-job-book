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

package io.github.xf8b.atsjobbook.startjob

import io.github.xf8b.atsjobbook.utility.EventBus
import io.github.xf8b.atsjobbook.utility.StandardEventType
import io.github.xf8b.atsjobbook.utility.WindowFactory
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
            startingCityProperty.set("")
            // change the starting cities available to the new ones
            startingCitiesAvailableProperty.get().setAll(model.startingCitiesAvailable)
        }

        model.eventBus.subscribe(StartJobEventType.ENDING_CITIES_AVAILABLE_UPDATED) {
            // reset the value property
            endingCityProperty.set("")
            // change the ending cities available to the new ones
            endingCitiesAvailableProperty.get().setAll(model.endingCitiesAvailable)
        }

        model.eventBus.subscribe(StartJobEventType.SAVE_COMPLETED) {
            this.eventBus.publish(StandardEventType.CLOSE_WINDOW)
        }

        model.eventBus.subscribe(StartJobEventType.SAVE_ERROR) {
            windowFactory.createErrorAlert(
                headerKey = "error.save.header",
                contentKey = "error.save.content"
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

        if (startingState.isBlank()
            || startingCity.isBlank()
            || endingState.isBlank()
            || endingCity.isBlank()
            || startingCompany.isBlank()
            || endingCompany.isBlank()
            || loadType.isBlank()
            || loadWeight.isBlank()
            || loadWeightMeasurement.isBlank()
            || dayOfWeek.isBlank()
            || hour.isBlank()
            || minute.isBlank()
        ) {
            windowFactory.createErrorAlert(
                headerKey = "error.incomplete_input.header",
                contentKey = "error.incomplete_input.content"
            )

            return
        }

        val loadWeightAsInt = loadWeight.toIntOrNull()

        if (loadWeightAsInt == null) {
            windowFactory.createErrorAlert(
                headerKey = "error.load_weight.header",
                contentKey = "error.load_weight.content.not_integer"
            )
            return
        } else if (loadWeightAsInt <= 0) {
            windowFactory.createErrorAlert(
                headerKey = "error.load_weight.header",
                contentKey = "error.load_weight.content.not_in_bounds"
            )

            return
        }

        val hourAsInt = hour.toIntOrNull()

        if (hourAsInt == null) {
            windowFactory.createErrorAlert(
                headerKey = "error.hour.header",
                contentKey = "error.hour.content.not_integer"
            )

            return
        } else if (hourAsInt !in 0..23) {
            windowFactory.createErrorAlert(
                headerKey = "error.hour.header",
                contentKey = "error.hour.content.not_in_bounds"
            )

            return
        }

        val minuteAsInt = minute.toIntOrNull()

        if (minuteAsInt == null) {
            windowFactory.createErrorAlert(
                headerKey = "error.minute.header",
                contentKey = "error.minute.content.not_integer"
            )

            return
        } else if (minuteAsInt !in 0..59) {
            windowFactory.createErrorAlert(
                headerKey = "error.minute.header",
                contentKey = "error.minute.content.not_in_bounds"
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