package api

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutingTest {

    @Test
    fun `test directory is answering`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/test")) {
            assertEquals(expected = HttpStatusCode.OK, actual = response.status())
        }
    }
}
