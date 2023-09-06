package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.port.JoinUserCommand

interface JoinUserUseCase {

    fun join(command: JoinUserCommand)
}
