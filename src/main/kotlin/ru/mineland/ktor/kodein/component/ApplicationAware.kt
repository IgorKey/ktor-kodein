package ru.mineland.ktor.kodein.component

import io.ktor.application.Application
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

abstract class ApplicationAware : KodeinAware {
    /**
     * Injected dependency with the current [Application].
     */
    val application: Application by instance()
}