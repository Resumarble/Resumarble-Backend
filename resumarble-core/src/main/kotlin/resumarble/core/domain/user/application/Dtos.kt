package resumarble.core.domain.user.application

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.jwt.CreateTokenCommand

data class JoinUserCommand(
    val account: String,
    val password: String
) {
    fun toDomain() = User(
        account = account,
        password = Password(password),
        provider = JwtProvider.RESUMARBLE
    )
}

data class LoginUserCommand(
    val account: String,
    val password: String
) {
    fun toTokenCommand(user: User) = CreateTokenCommand(
        userId = user.userId,
        account = user.account,
        provider = user.provider,
        role = user.role
    )
}

data class LogoutUserCommand(
    val userId: Long
) {
    companion object {
        @JvmStatic
        fun of(userId: Long) = LogoutUserCommand(userId)
    }
}

data class ReissueTokenCommand(
    val accessToken: String,
    val refreshToken: String
)
