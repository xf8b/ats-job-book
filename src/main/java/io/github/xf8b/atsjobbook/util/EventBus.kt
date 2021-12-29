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

import javafx.application.Platform

/**
 * A simple implementation of an event bus (or subscriber system).
 *
 * Event types must implement [EventType].
 */
class EventBus {
    private val subscribers = mutableMapOf<EventType, MutableList<() -> Unit>>()

    /**
     * Subscribe to a specific event; when that event fires, you will be notified via [subscriber].
     */
    fun subscribe(event: EventType, subscriber: () -> Unit) {
        // if no subscribers with this event have been added yet, create an empty list for the event
        if (!subscribers.containsKey(event)) subscribers[event] = mutableListOf()

        // add the subscriber
        subscribers[event]?.plusAssign(subscriber)
    }

    /**
     * Publish an event, notifying all the subscribers that subscribed to this event type.
     */
    fun publish(event: EventType) {
        Platform.runLater {
            // fire each subscriber (if any have subscribed)
            subscribers[event]?.forEach { it() }
        }
    }
}

/**
 * An event type.
 *
 * You are required to implement this for your events to use it in the [EventBus] system.
 */
interface EventType

enum class StandardEventType : EventType {
    CLOSE_WINDOW,
}