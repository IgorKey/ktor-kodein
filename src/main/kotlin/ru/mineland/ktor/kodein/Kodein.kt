package ru.mineland.ktor.kodein

import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.application
import io.ktor.routing.routing
import io.ktor.util.AttributeKey
import org.kodein.di.Instance
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.jvmType
import ru.mineland.ktor.kodein.component.KodeinController

class Kodein {

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, org.kodein.di.Kodein.MainBuilder, Kodein> {

        override val key = AttributeKey<Kodein>("Kodein")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: org.kodein.di.Kodein.MainBuilder.() -> Unit
        ): Kodein {
            val feature = Kodein()

            pipeline.intercept(ApplicationCallPipeline.Features) {
                val kodein = org.kodein.di.Kodein {
                    configure(this)
                    bind<Application>() with instance(application)
                }
                application.apply { registerControllers(kodein) }
            }

            return feature
        }

        /**
         * Detects all the registered [KodeinController] and registers its routes.
         */
        fun Application.registerControllers(kodein: org.kodein.di.Kodein) {
            routing {
                for (bind in kodein.container.tree.bindings) {
                    val bindClass = bind.key.type.jvmType as? Class<*>?
                    if (bindClass != null && KodeinController::class.java.isAssignableFrom(bindClass)) {
                        val res by kodein.Instance(bind.key.type)
                        println("Registering '$res' routes...")
                        (res as KodeinController).apply { registerRoutes() }
                    }
                }
            }
        }
    }
}
