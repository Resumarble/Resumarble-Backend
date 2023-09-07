package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.port.LoginUserCommand
import resumarble.core.global.jwt.LoginToken

interface LoginUserUseCase {
    fun login(command: LoginUserCommand): LoginToken
}
