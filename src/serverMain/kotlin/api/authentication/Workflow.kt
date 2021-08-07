package api.authentication

import java.net.URLEncoder

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

fun extractToken(callbackToken: String?): String? {
    return if (callbackToken.isNullOrBlank()) null
    else
        callbackToken.split("access_token=")[1].split("&")[0]
}
