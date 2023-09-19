package resumarble.api.user

import jakarta.validation.constraints.NotBlank
import resumarble.core.domain.user.application.JoinUserCommand
import resumarble.core.domain.user.application.LoginUserCommand
import resumarble.core.domain.user.application.ReissueTokenCommand

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

data class ReissueTokenRequest(
    val accessToken: String,
    val refreshToken: String
) {
    fun toCommand() = ReissueTokenCommand(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

data class DuplicateAccountRequest(
    val account: String
)
