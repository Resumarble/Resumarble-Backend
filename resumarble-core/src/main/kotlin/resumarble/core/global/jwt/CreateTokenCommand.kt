package resumarble.core.global.jwt

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.constraints.UserRole
import resumarble.core.domain.user.domain.User

data class CreateTokenCommand(
    val userId: Long,
    val account: String,
    val provider: JwtProvider,
    val role: UserRole
) {
    companion object {
        @JvmStatic
        fun toTokenCommand(user: User) = CreateTokenCommand(
            userId = user.userId,
            account = user.account,
            provider = user.provider,
            role = user.role
        )
    }
}
