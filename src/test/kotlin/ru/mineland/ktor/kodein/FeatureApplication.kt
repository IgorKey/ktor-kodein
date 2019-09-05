package ru.mineland.ktor.kodein

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Application.main() {
    install(Kodein) {
        bind<String>("") with singleton { "TEST" }
    }
    routing {
        get("/") {
            call.respondText("",
                ContentType.Application.Any,
                HttpStatusCode.Accepted
            )
        }
    }
}
