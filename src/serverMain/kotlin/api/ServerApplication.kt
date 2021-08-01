package api

import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8000) {
        routing {
            liveness()
            getToken()
            redirectUser()
        }
    }.start(wait = true)
}
