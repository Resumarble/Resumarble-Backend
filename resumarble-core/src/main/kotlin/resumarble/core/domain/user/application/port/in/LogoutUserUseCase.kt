package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.port.LogoutUserCommand

interface LogoutUserUseCase {
    fun logout(command: LogoutUserCommand)
}
