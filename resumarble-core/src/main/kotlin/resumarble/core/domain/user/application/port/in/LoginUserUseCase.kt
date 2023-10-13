package resumarble.core.domain.user.application.port.`in`

import resumarble.core.domain.user.application.LoginUserCommand
import resumarble.core.domain.user.application.ReissueTokenCommand
import resumarble.core.global.jwt.LoginToken

interface LoginUserUseCase {
    fun login(command: LoginUserCommand): LoginToken

    fun reissueToken(command: ReissueTokenCommand): LoginToken
}
