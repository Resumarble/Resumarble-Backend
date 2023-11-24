package resumarble.core.domain.user.application

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.jwt.CreateTokenCommand

data class JoinUserCommand(
    val account: String,
    val password: String,
    val provider: JwtProvider = JwtProvider.RESUMARBLE
) {
    fun toDomain() = User(
        account = account,
        password = Password(password),
        provider = provider
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

data class DuplicateAccountCommand(
    val account: String
) {
    companion object {
        @JvmStatic
        fun of(account: String) = DuplicateAccountCommand(account)
    }
}

data class ReissueTokenCommand(
    val accessToken: String,
    val refreshToken: String
)
