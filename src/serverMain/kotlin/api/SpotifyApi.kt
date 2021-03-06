package api

import api.authentication.AuthenticationDetails
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"
const val LIST_PLAYLIST_URL = "me/playlists"

class SpotifyApi(
    private val authenticationDetails: AuthenticationDetails,
    private val client: HttpClient,
) {
    suspend fun getUsersPlaylists(limit: Int, offset: Int): String {
        return client.get(SPOTIFY_BASE_URL + LIST_PLAYLIST_URL) {
            parameter("limit", limit)
            parameter("offset", offset)

            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${authenticationDetails.accessToken}")
        }
    }
}
