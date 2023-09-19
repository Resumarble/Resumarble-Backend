package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.DuplicateAccountCommand
import resumarble.core.domain.user.application.JoinUserCommand

interface JoinUserUseCase {

    fun join(command: JoinUserCommand)

    fun duplicateAccount(command: DuplicateAccountCommand)
}
