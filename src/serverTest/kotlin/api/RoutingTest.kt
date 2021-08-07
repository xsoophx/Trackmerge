package api

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import logger
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutingTest {

    companion object {
        val logger = logger<RoutingTest>()
    }

    @Test
    fun `test directory is answering with correct status code`(): Unit = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/test")) {
            assertEquals(expected = HttpStatusCode.OK, actual = response.status())
        }
    }

    @Test
    fun `test directory is containing correct message`(): Unit = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/test")) {
            response.content?.let { assert(it.contains("I'm alive!")) }
        }
    }

    @Test
    fun `redirect responds with correct status code`(): Unit = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertEquals(expected = HttpStatusCode.Found, actual = response.status())
        }
    }

    @Test
    fun `callback directory is extracting correct token`(): Unit = withTestApplication(Application::module) {
        val testRedirectUri =
            "#access_token=BQApOM4Iya-HDKhGu9Z7rodv7eYQNneSUqwyuMLgjlWM80UvTmu-4oNiB6_" +
                "qAuSFyTzLUWWYYLXd0qOOftOkxU8zBk7RYfyaP5q17ceU8ZIdFXY2ltoorvqDcBwheQJ6" +
                "wLUqr3b9t6zrAE4&token_type=Bearer&expires_in=3600"

        val accessToken = "BQApOM4Iya-HDKhGu9Z7rodv7eYQNneSUqwyuMLgjlWM80UvTmu-4oNiB6_" +
            "qAuSFyTzLUWWYYLXd0qOOftOkxU8zBk7RYfyaP5q17ceU8ZIdFXY2ltoorvqDcBwheQJ6" +
            "wLUqr3b9t6zrAE4"

        with(handleRequest(HttpMethod.Get, "/callback$testRedirectUri")) {
            logger.info("callback directory is returning the following token: ${response.content}")
            assertNotNull(response.content)
            response.content?.let { assert(it.contains(accessToken)) }
        }
    }
}
