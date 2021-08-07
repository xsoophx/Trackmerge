package api

import io.ktor.application.Application
import io.ktor.routing.routing

fun Application.module() {
    routing {
        liveness()
        getToken()
        redirectUser()
    }
}
