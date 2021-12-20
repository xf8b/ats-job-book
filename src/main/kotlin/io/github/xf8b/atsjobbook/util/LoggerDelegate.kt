package io.github.xf8b.atsjobbook.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty

class LoggerDelegate<in T : Any> {
    private lateinit var logger: Logger

    operator fun getValue(thisRef: T, property: KProperty<*>): Logger =
        if (!::logger.isInitialized) LoggerFactory.getLogger(thisRef.javaClass)
        else logger
}