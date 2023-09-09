package resumarble.core.global.jwt

import resumarble.core.domain.user.constraints.JwtProvider

data class CreateTokenCommand(
    val userId: Long,
    val email: String,
    val nickname: String,
    val provider: JwtProvider
)
