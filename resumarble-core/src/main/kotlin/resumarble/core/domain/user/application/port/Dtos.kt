package resumarble.core.domain.user.application.port

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.jwt.CreateTokenCommand

data class JoinUserCommand(
    val email: String,
    val password: String,
    val nickname: String
) {
    fun toDomain() = User(
        email = email,
        password = Password(password),
        nickname = nickname,
        provider = JwtProvider.RESUMARBLE
    )
}

data class LoginUserCommand(
    val email: String,
    val password: String
) {
    fun toTokenCommand(user: User) = CreateTokenCommand(
        userId = user.userId,
        email = user.email,
        nickname = user.nickname,
        provider = user.provider,
        role = user.role
    )
}

data class LogoutUserCommand(
    val email: String,
    val accessToken: String
)
