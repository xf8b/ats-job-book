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
import io.github.xf8b.atsjobbook.util.STATES
import io.github.xf8b.atsjobbook.viewmodel.StartJobViewModel
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.ComboBox

class StartJobView : View {
    override val root: Parent
        get() = Resources.loadFxml("start-job.fxml")
    private val viewModel = StartJobViewModel()

    @FXML
    private lateinit var stateComboBox: ComboBox<String>

    @FXML
    private lateinit var cityComboBox: ComboBox<String>

    @FXML
    private fun initialize() {
        // add all the states so that it's available
        // this does NOT add the cities, since it will be changed depending on the state
        stateComboBox.items.addAll(STATES)

        // arrow in one direction means it DEPENDS on the other (e.g. a -> b means a depends on b)
        // viewModel.selectedState -> this.selectedState
        viewModel.selectedStateProperty.bind(stateComboBox.valueProperty())
        // viewModel.selectedCity <-> this.selectedCity
        viewModel.selectedCityProperty.bindBidirectional(cityComboBox.valueProperty())
        // viewModel.cityChoices <-> this.cityChoices
        viewModel.cityChoicesProperty.bindBidirectional(cityComboBox.itemsProperty())
    }

    fun onStateChange() {
        viewModel.onStateChange()
    }

    fun onCityChange() {
        viewModel.onCityChange()
    }
}