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

package io.github.xf8b.atsjobbook.view

import io.github.xf8b.atsjobbook.util.DefaultWindowFactory
import io.github.xf8b.atsjobbook.util.Resources
import io.github.xf8b.atsjobbook.util.StandardEventType
import io.github.xf8b.atsjobbook.viewmodel.StartJobViewModel
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField

class StartJobView : View {
    override val root: Parent
        get() = Resources.loadFxml("start-job.fxml")
    private val viewModel = StartJobViewModel(DefaultWindowFactory())

    @FXML
    private lateinit var startingStateComboBox: ComboBox<String>

    @FXML
    private lateinit var startingCityComboBox: ComboBox<String>

    @FXML
    private lateinit var endingStateComboBox: ComboBox<String>

    @FXML
    private lateinit var endingCityComboBox: ComboBox<String>

    @FXML
    private lateinit var startingCompanyComboBox: ComboBox<String>

    @FXML
    private lateinit var endingCompanyComboBox: ComboBox<String>

    @FXML
    private lateinit var loadTypeTextField: TextField

    @FXML
    private lateinit var loadWeightTextField: TextField

    @FXML
    private lateinit var loadWeightMeasurementComboBox: ComboBox<String>

    @FXML
    private lateinit var dayOfWeekComboBox: ComboBox<String>

    @FXML
    private lateinit var hourTextField: TextField

    @FXML
    private lateinit var minuteTextField: TextField

    @FXML
    private fun initialize() {
        // if the view model asks to close the current window, close it
        viewModel.eventBus.subscribe(StandardEventType.CLOSE_WINDOW) {
            // use something random in the scene to get the window
            startingStateComboBox.scene.window.hide()
        }

        // load the states and companies from the resources
        val states = Resources.loadStates()
        val companies = Resources.loadCompanies()

        // add all the states
        // this does NOT add the cities, since that will be changed depending on the state
        startingStateComboBox.items.addAll(states)
        endingStateComboBox.items.addAll(states)

        // add all the companies
        startingCompanyComboBox.items.addAll(companies)
        endingCompanyComboBox.items.addAll(companies)

        // set up the bindings for starting city
        // view model starting state depends on our starting state
        viewModel.startingStateProperty.bind(startingStateComboBox.valueProperty())
        // view model starting city and our starting city depend on each other
        viewModel.startingCityProperty.bindBidirectional(startingCityComboBox.valueProperty())
        // view model starting city choices and our starting city choices depend on each other
        viewModel.startingCitiesAvailableProperty.bindBidirectional(startingCityComboBox.itemsProperty())

        // set up the bindings for ending city
        // view model ending state depends on our ending state
        viewModel.endingStateProperty.bind(endingStateComboBox.valueProperty())
        // view model ending city and our ending city depend on each other
        viewModel.endingCityProperty.bindBidirectional(endingCityComboBox.valueProperty())
        // view model ending city choices and our ending city choices depend on each other
        viewModel.endingCitiesAvailableProperty.bindBidirectional(endingCityComboBox.itemsProperty())

        // set up the bindings for starting and ending company
        // view model starting company depends on our starting company
        viewModel.startingCompanyProperty.bind(startingCompanyComboBox.valueProperty())
        // view model ending company depends on our ending company
        viewModel.endingCompanyProperty.bind(endingCompanyComboBox.valueProperty())

        // view model load type depends on our load type
        viewModel.loadTypeProperty.bind(loadTypeTextField.textProperty())
        // view model load weight depends on our load weight
        viewModel.loadWeightProperty.bind(loadWeightTextField.textProperty())
        // view model load weight measurement depends on our load weight measurement
        viewModel.loadWeightMeasurementProperty.bind(loadWeightMeasurementComboBox.valueProperty())

        // view model day of week depends on our day of week
        viewModel.dayOfWeekProperty.bind(dayOfWeekComboBox.valueProperty())
        // view model hour depends on our hour
        viewModel.hourProperty.bind(hourTextField.textProperty())
        // view model minute depends on our minute
        viewModel.minuteProperty.bind(minuteTextField.textProperty())
    }

    @FXML
    private fun onStartingStateChange() {
        viewModel.onStartingStateChange()
    }

    @FXML
    private fun onEndingStateChange() {
        viewModel.onEndingStateChange()
    }

    @FXML
    private fun onSaveButtonPressed() {
        viewModel.onSaveButtonPressed()
    }
}