@file:JvmName("Main")

package io.github.xf8b.atsjobbook

import io.github.xf8b.atsjobbook.util.LoggerDelegate

class Main {
    companion object {
        private val LOGGER by LoggerDelegate()

        @JvmStatic
        fun main(vararg args: String) {
            LOGGER.error("This worked!")
        }
    }
}