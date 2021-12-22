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

package io.github.xf8b.atsjobbook.util

/**
 * A list of all the American Truck Simulator states, in alphabetical order.
 */
val STATES = listOf(
    "Arizona",
    "California",
    "Colorado",
    "Idaho",
    "Nevada",
    "New Mexico",
    "Oregon",
    "Utah",
    "Washington",
    "Wyoming",
).sorted()

/**
 * A map of all the American Truck Simulator cities, in alphabetical order.
 *
 * The map structure is STATE to CITIES.
 */
val CITIES = mapOf(
    "Arizona" to listOf(
        "Camp Verde",
        "Clifton",
        "Ehrenberg",
        "Flagstaff",
        "Grand Canyon Village",
        "Holbrook",
        "Kayenta",
        "Kingman",
        "Nogales",
        "Page",
        "Phoenix",
        "San Simon",
        "Show Low",
        "Sierra Vista",
        "Tuscon",
        "Yuma",
    ).sorted(),
    "California" to listOf("a").sorted(),
    "Colorado" to listOf("a").sorted(),
    "Idaho" to listOf("a").sorted(),
    "Nevada" to listOf("a").sorted(),
    "New Mexico" to listOf("a").sorted(),
    "Oregon" to listOf("a").sorted(),
    "Utah" to listOf("a").sorted(),
    "Washington" to listOf("a").sorted(),
    "Wyoming" to listOf("a").sorted(),
)