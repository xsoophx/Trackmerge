package api

import Playlists
import api.authentication.AuthenticationDetails
import api.authentication.TokenType
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle


@TestInstance(Lifecycle.PER_CLASS)
class SpotifyApiTest {

    @Test
    fun `Spotify is returning Json Object`() {

        val authenticationDetails = AuthenticationDetails(
            accessToken = "accessToken",
            tokenType = TokenType.BEARER,
            expiresIn = 3600
        )
        val client = mockk<HttpClient>()
        val playlists = Playlists(href = "", items = listOf())

        val spotifyApiRequest = SpotifyApi(authenticationDetails, client)

        coEvery { client.get<Playlists>(any<String>(), any<HttpRequestBuilder.() -> Unit>()) } returns playlists

        val receivedPlaylists = runBlocking { spotifyApiRequest.getUsersPlaylists(10, 0) }
        assertEquals(expected = playlists, actual = receivedPlaylists)

        coVerify {
            client.get<Playlists>(
                "https://api.spotify.com/v1/me/playlists",
                any<HttpRequestBuilder.() -> Unit>()
            )
        }
        confirmVerified(client)
    }
}
