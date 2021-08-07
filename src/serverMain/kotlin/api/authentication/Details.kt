package api

data class AuthenticationDetails(
    val accessToken: String,
    val tokenType: TokenType,

)

enum class TokenType(val token: String) {
    BEARER("Bearer")
}
