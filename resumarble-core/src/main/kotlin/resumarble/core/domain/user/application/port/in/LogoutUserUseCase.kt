package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.LogoutUserCommand

interface LogoutUserUseCase {
    fun logout(command: LogoutUserCommand)
}
