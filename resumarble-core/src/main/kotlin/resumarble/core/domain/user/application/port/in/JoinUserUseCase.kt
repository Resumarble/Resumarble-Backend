package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.DuplicateAccountCommand
import resumarble.core.domain.user.application.JoinUserCommand
import resumarble.core.domain.user.domain.User

interface JoinUserUseCase {

    fun join(command: JoinUserCommand): User

    fun duplicateAccount(command: DuplicateAccountCommand)
}
