package api

import Playlists
import api.authentication.AuthenticationDetails
import api.authentication.TokenType
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.specto.hoverfly.junit.core.Hoverfly
import io.specto.hoverfly.junit.core.HoverflyMode
import io.specto.hoverfly.junit.core.SimulationSource.dsl
import io.specto.hoverfly.junit.dsl.HoverflyDsl.service
import io.specto.hoverfly.junit.dsl.ResponseCreators.success
import io.specto.hoverfly.junit5.HoverflyExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

@ExtendWith(HoverflyExtension::class)
@TestInstance(Lifecycle.PER_CLASS)
class SpotifyApiTest {

    companion object {
        val authenticationDetails = AuthenticationDetails(
            accessToken = "accessToken",
            tokenType = TokenType.BEARER,
            expiresIn = 3600
        )

        // language = json
        const val hoverflyResponse = """
        {
            "href": "https://api.spotify.com/v1/users/123/playlists?offset=0&limit=1",
            "items": [
            {
                "collaborative": false,
                "description": "",
                "external_urls": {
                "spotify": "https://open.spotify.com/playlist/abc"
            },
                "href": "https://api.spotify.com/v1/playlists/abc",
                "id": "abc",
                "images": [
                {
                    "height": 640,
                    "url": "https://some.url.com",
                    "width": 640
                },
                {
                    "height": 300,
                    "url": "https://some.url.com",
                    "width": 300
                },
                {
                    "height": 60,
                    "url": "https://some.url.com",
                    "width": 60
                }
                ],
                "name": "PlaylistName",
                "owner": {
                "display_name": "User Name",
                "external_urls": {
                "spotify": "https://open.spotify.com/user/123"
            },
                "href": "https://api.spotify.com/v1/users/123",
                "id": "123",
                "type": "user",
                "uri": "spotify:user:123"
            },
                "primary_color": null,
                "public": true,
                "snapshot_id": "dummySnapshotId",
                "tracks": {
                "href": "https://api.spotify.com/v1/playlists/abc/tracks",
                "total": 16
            },
                "type": "playlist",
                "uri": "spotify:playlist:abc"
            }
            ],
            "limit": 1,
            "next": "https://api.spotify.com/v1/users/123/playlists?offset=1&limit=1",
            "offset": 0,
            "previous": null,
            "total": 211
        }
        """
    }

    @ParameterizedTest
    @CsvSource("0, 1")
    fun `Spotify is returning Json Object`(limit: Int, offset: Int) {

        Hoverfly(HoverflyMode.SIMULATE).use { hoverfly ->
            hoverfly.simulate(
                dsl(
                    service(SPOTIFY_BASE_URL)
                        .get(LIST_PLAYLIST_URL)
                        .header("Authorization", authenticationDetails.accessToken)
                        .queryParam("limit", limit)
                        .queryParam("offset", offset)
                        .willReturn(success(hoverflyResponse, "application/json"))
                )
            )

            val client = mockk<HttpClient>()
            val spotifyApiRequest = SpotifyApi(authenticationDetails, client)

            val receivedPlaylists = runBlocking { spotifyApiRequest.getUsersPlaylists(limit = limit, offset = offset) }

            assertEquals(expected = hoverflyResponse, actual = receivedPlaylists)

            coVerify {
                client.get<Playlists>(
                    "https://api.spotify.com/v1/me/playlists",
                    any<HttpRequestBuilder.() -> Unit>()
                )
            }
            confirmVerified(client)
        }
    }
}
