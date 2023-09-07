package resumarble.core.domain.user.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.user.application.port.LoginUserCommand
import resumarble.core.domain.user.application.port.`in`.LoginUserUseCase
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.jwt.JwtTokenProvider
import resumarble.core.global.jwt.LoginToken

@Service
class LoginService(
    private val findUserPort: FindUserPort,
    private val jwtTokenProvider: JwtTokenProvider
) : LoginUserUseCase {

    @Transactional(readOnly = true)
    override fun login(command: LoginUserCommand): LoginToken {
        val user = findUserPort.findUserByEmail(command.email)
        user.authenticate(Password(command.password))
        return jwtTokenProvider.createToken(command.toTokenCommand(user))
    }
}
