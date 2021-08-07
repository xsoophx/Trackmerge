package api

import api.authentication.createAuthUrl
import api.authentication.extractToken
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.html.body
import kotlinx.html.p

fun Route.redirectUser() {
    get("/") {
        val redirectUrl = createAuthUrl()
        call.respondRedirect(redirectUrl)
    }
}

fun Route.getToken() {
    get("/callback#{id}") {
        val token = call.parameters["id"]
        call.respondHtml {
            body {
                p {
                    +"Your token is ${extractToken(token)}."
                }
            }
        }
    }
}

// rather a "test" endpoint
fun Route.liveness() {
    get("/test") {
        call.respondHtml {
            body {
                p {
                    +"I'm alive!"
                }
            }
        }
    }
}