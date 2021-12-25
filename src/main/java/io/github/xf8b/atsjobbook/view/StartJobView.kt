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

import io.github.xf8b.atsjobbook.util.Resources
import io.github.xf8b.atsjobbook.viewmodel.StartJobViewModel
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField

class StartJobView : View {
    override val root: Parent
        get() = Resources.loadFxml("start-job.fxml")
    private val viewModel = StartJobViewModel()

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
    private fun initialize() {
        val states = Resources.loadStates()
        val companies = Resources.loadCompanies()
        // add all the states so that it's available
        // this does NOT add the cities, since it will be changed depending on the state
        startingStateComboBox.items.addAll(states)
        endingStateComboBox.items.addAll(states)

        startingCompanyComboBox.items.addAll(companies)
        endingCompanyComboBox.items.addAll(companies)

        // set up the bindings for starting city
        // view model starting state depends on our starting state
        viewModel.startingStateProperty.bind(startingStateComboBox.valueProperty())
        // view model starting city and our starting city depend on each other
        viewModel.startingCityProperty.bindBidirectional(startingCityComboBox.valueProperty())
        // view model starting city choices and our starting city choices depend on each other
        viewModel.startingCityChoicesProperty.bindBidirectional(startingCityComboBox.itemsProperty())

        // set up the bindings for ending city
        viewModel.endingStateProperty.bind(endingStateComboBox.valueProperty())
        viewModel.endingCityProperty.bindBidirectional(endingCityComboBox.valueProperty())
        viewModel.endingCityChoicesProperty.bindBidirectional(endingCityComboBox.itemsProperty())

        // set up the bindings for starting and ending company
        viewModel.startingCompanyProperty.bind(startingCompanyComboBox.valueProperty())
        viewModel.endingCompanyProperty.bind(endingCompanyComboBox.valueProperty())

        viewModel.loadTypeProperty.bind(loadTypeTextField.textProperty())
        viewModel.loadWeightProperty.bind(loadWeightTextField.textProperty())
        viewModel.loadWeightMeasurementProperty.bind(loadWeightMeasurementComboBox.valueProperty())
    }

    fun onStartingStateChange() {
        viewModel.onStartingStateChange()
    }

    fun onStartingCityChange() {
        viewModel.onStartingCityChange()
    }

    fun onEndingStateChange() {
        viewModel.onEndingStateChange()
    }

    fun onEndingCityChange() {
        viewModel.onEndingCityChange()
    }

    fun onStartingCompanyChange() {
        viewModel.onStartingCompanyChange()
    }

    fun onEndingCompanyChange() {
        viewModel.onEndingCompanyChange()
    }

    fun onLoadTypeChange() {
        viewModel.onLoadTypeChange()
    }

    fun onLoadWeightChange() {
        viewModel.onLoadWeightChange()
    }

    fun onLoadWeightMeasurementChange() {
        viewModel.onLoadWeightMeasurementChange()
    }
}