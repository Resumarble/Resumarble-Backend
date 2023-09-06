package resumarble.core.domain.user.application.port

import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password

data class JoinUserCommand(
    val email: String,
    val password: String,
    val nickname: String
) {
    fun toDomain() = User(
        email = email,
        password = Password(password),
        nickname = nickname
    )
}

data class LoginUserCommand(
    val email: String,
    val password: String
)
