package resumarble.core.global.jwt

data class LoginToken(
    val accessToken: String,
    val refreshToken: String
)
