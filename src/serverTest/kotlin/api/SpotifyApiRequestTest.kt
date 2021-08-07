package api

import api.authentication.AuthenticationDetails
import api.authentication.TokenType
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.withTestApplication
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.Before


class SpotifyApiRequestTest {

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun `Spotify is returning Json Object`(): Unit = withTestApplication(Application::module) {
        @MockK
        val authenticationDetails = mockk<AuthenticationDetails>()

        @InjectMockKs
        val spotifyApiRequest = SpotifyApiRequest(authenticationDetails)

        every { authenticationDetails.accessToken } returns ","
        every { authenticationDetails.tokenType } returns TokenType.BEARER
        every { authenticationDetails.expiresIn } returns 3600


    }
}
