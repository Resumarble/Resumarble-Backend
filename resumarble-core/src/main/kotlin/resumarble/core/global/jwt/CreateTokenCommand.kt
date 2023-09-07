package resumarble.core.global.jwt

data class CreateTokenCommand(
    val userId: Long,
    val email: String,
    val nickname: String
)
