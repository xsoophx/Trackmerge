package api

import api.authentication.createAuthUrl
import api.authentication.extractToken
import api.authentication.getPlaylists
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import java.security.InvalidParameterException
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

        try {
            val credentials = extractToken(token)
            getPlaylists(credentials)
        } catch (e: InvalidParameterException) {
            call.respondHtml {
                body {
                    p {
                        +(e.message?: "an unknown error occurred.")
                    }
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
