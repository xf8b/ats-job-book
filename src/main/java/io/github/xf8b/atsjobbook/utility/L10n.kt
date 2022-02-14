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

package io.github.xf8b.atsjobbook.utility

import java.util.*

val RESOURCE_BUNDLE: ResourceBundle by lazy {
    val locale = try {
        Locale.forLanguageTag(PROPERTIES.getProperty("locale").replace(oldValue = "_", newValue = "-"))
    } catch (exception: Exception) {
        throw IllegalArgumentException("Invalid locale", exception)
    }

    return@lazy ResourceBundle.getBundle("io.github.xf8b.atsjobbook.i18n.atsjobbook", locale)
}

fun getLocalizedString(key: String): String = RESOURCE_BUNDLE.getString(key)