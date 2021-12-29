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

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty

/**
 * It just makes the code look nicer.
 */
class LoggerDelegate<in T : Any> {
    private lateinit var logger: Logger

    operator fun getValue(thisRef: T, property: KProperty<*>): Logger {
        if (!::logger.isInitialized) {
            // if the logger has not been set, set it
            logger = LoggerFactory.getLogger(
                // if thisRef is a companion object, use the enclosing class instead
                if (thisRef::class.isCompanion) thisRef.javaClass.enclosingClass
                // else just use thisRef
                else thisRef.javaClass
            )
        }

        return logger
    }
}