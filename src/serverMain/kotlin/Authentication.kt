import java.net.URLEncoder

const val CLIENT_ID = "57b8759dc45246eb9e1434cedbaf5e44"
const val REDIRECT_URI = "https://127.0.0.1:9999/index.html" //TODO: add "real" URL


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

