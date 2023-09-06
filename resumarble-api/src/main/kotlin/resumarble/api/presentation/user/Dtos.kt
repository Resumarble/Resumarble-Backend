package resumarble.api.presentation.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import resumarble.core.domain.user.application.port.JoinUserCommand
import resumarble.core.domain.user.application.port.LoginUserCommand

data class JoinUserRequest(
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank
    val email: String,

    @NotBlank
    val password: String,
    @NotBlank
    val nickname: String
) {
    fun toCommand() = JoinUserCommand(
        email = email,
        password = password,
        nickname = nickname
    )
}

data class LoginUserRequest(
    private val email: String,
    private val password: String
) {
    fun toCommand() = LoginUserCommand(
        email = email,
        password = password
    )
}
