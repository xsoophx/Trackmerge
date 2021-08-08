package api.authentication

data class AuthenticationDetails(
    val accessToken: String,
    val tokenType: TokenType,
    val expiresIn: Int,
)

enum class TokenType(val token: String) {
    BEARER("Bearer");

    companion object {
        val byName = values().associateBy(TokenType::token)
    }
}
