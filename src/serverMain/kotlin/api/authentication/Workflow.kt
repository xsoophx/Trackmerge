package api.authentication

import api.SpotifyApi
import api.authentication.TokenType.Companion.byName
import io.ktor.client.HttpClient
import java.net.URLEncoder
import java.security.InvalidParameterException

const val CLIENT_ID = "57b8759dc45246eb9e1434cedbaf5e44"
const val REDIRECT_URI = "https://suffro.cc/trackmerge/callback"

fun createAuthUrl(
    scopes: Map<String, String> = emptyMap(),
    redirectUri: String = REDIRECT_URI
): String {
    return "https://accounts.spotify.com/authorize?client_id=$CLIENT_ID&" +
        "response_type=token&redirect_uri=${URLEncoder.encode(redirectUri, "utf-8")}" +
        "&show_dialog=true&scope=${encodeScope(scopes)}"
}

private fun encodeScope(scopes: Map<String, String>): String {
    return ""
    // TODO:
}

fun extractToken(callbackToken: String?): AuthenticationDetails {
    val parameters = (callbackToken ?: "").splitToSequence("&").map { param ->
        val (key, value) = param.split("=")
        key to value
    }.toMap()

    return AuthenticationDetails(
        accessToken = parameters["access_token"] ?: throw InvalidParameterException("acess_token is missing!"),
        tokenType = byName[parameters["token_type"]] ?: throw InvalidParameterException("token_type is missing!"),
        expiresIn = parameters["expires_in"]?.toInt() ?: throw InvalidParameterException("expires_in is missing!")
    ) // TODO: extract expire
}

suspend fun getPlaylists(authenticationDetails: AuthenticationDetails) {
    val spotifyApi = SpotifyApi(authenticationDetails = authenticationDetails, client = HttpClient())
    val response = spotifyApi.getUsersPlaylists(limit = 1, offset = 1)
}
