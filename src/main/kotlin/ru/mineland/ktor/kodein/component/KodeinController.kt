package ru.mineland.ktor.kodein.component

import io.ktor.locations.locations
import io.ktor.routing.Routing
import org.kodein.di.Kodein

/**
 * A [KodeinAware] base class for Controllers handling routes.
 * It allows to easily get dependencies, and offers some useful extension like getting the [href] of a [TypedRoute].
 */
abstract class KodeinController(override val kodein: Kodein) : ApplicationAware() {

    /**
     * Shortcut to get the url of a [TypedRoute].
     */
    val TypedRoute.href get() = application.locations.href(this)

    /**
     * Method that subtypes must override to register the handled [Routing] routes.
     */
    abstract fun Routing.registerRoutes()
}

/**
 * Interface used for identify typed routes annotated with [Location].
 */
interface TypedRoute