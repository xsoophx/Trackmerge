import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8000) {
        routing {
            get("/") {
                val redirectUrl = createAuthUrl()
                call.respondRedirect(redirectUrl)
            }
        }
    }.start(wait = true)
}
