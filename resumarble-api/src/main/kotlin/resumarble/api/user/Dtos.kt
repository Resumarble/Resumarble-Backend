package resumarble.api.user

import jakarta.validation.constraints.NotBlank
import resumarble.core.domain.user.application.port.JoinUserCommand
import resumarble.core.domain.user.application.port.LoginUserCommand

data class JoinUserRequest(
    @NotBlank
    val account: String,
    @NotBlank
    val password: String
) {
    fun toCommand() = JoinUserCommand(
        account = account,
        password = password
    )
}

data class LoginUserRequest(
    val account: String,
    val password: String
) {
    fun toCommand() = LoginUserCommand(
        account = account,
        password = password
    )
}
