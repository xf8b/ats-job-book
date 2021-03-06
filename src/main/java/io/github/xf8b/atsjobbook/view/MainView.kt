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

package io.github.xf8b.atsjobbook.view

import io.github.xf8b.atsjobbook.utility.DefaultWindowFactory
import io.github.xf8b.atsjobbook.utility.loadFxml
import io.github.xf8b.atsjobbook.viewmodel.MainViewModel
import javafx.fxml.FXML

class MainView : View {
    private val windowFactory = DefaultWindowFactory()
    override val root by lazy { loadFxml("main.fxml", windowFactory) }
    private val viewModel = MainViewModel(windowFactory)

    @FXML
    private fun onStartJob() {
        viewModel.onStartJob()
    }
}