package resumarble.core.global.jwt

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.constraints.UserRole

data class CreateTokenCommand(
    val userId: Long,
    val email: String,
    val nickname: String,
    val provider: JwtProvider,
    val role: UserRole
)
