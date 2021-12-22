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
    private lateinit var state: ComboBox<String>

    @FXML
    private lateinit var city: ComboBox<String>

    @FXML
    private fun initialize() {
        state.items.addAll(STATES)

        viewModel.stateProperty.bind(state.valueProperty())
        viewModel.cityProperty.bind(city.valueProperty())
    }

    fun onStateChange() {
        viewModel.onStateChange()
    }

    fun onCityChange() {
        viewModel.onCityChange()
    }
}