package resumarble.core.domain.user.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.user.application.LoginUserCommand
import resumarble.core.domain.user.application.LogoutUserCommand
import resumarble.core.domain.user.application.ReissueTokenCommand
import resumarble.core.domain.user.application.port.`in`.LoginUserUseCase
import resumarble.core.domain.user.application.port.`in`.LogoutUserUseCase
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.error.UserNotFoundException
import resumarble.core.global.jwt.CreateTokenCommand
import resumarble.core.global.jwt.JwtTokenProvider
import resumarble.core.global.jwt.JwtVerifier
import resumarble.core.global.jwt.LoginToken

@Service
class LoginService(
    private val findUserPort: FindUserPort,
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtVerifier: JwtVerifier
) : LoginUserUseCase, LogoutUserUseCase {

    @Transactional(readOnly = true)
    override fun login(command: LoginUserCommand): LoginToken {
        val user = findUserPort.findUserByAccount(command.account) ?: throw UserNotFoundException()
        user.authenticate(Password(command.password))
        return jwtTokenProvider.createToken(command.toTokenCommand(user))
    }

    @Transactional(readOnly = true)
    override fun reissueToken(command: ReissueTokenCommand): LoginToken {
        val account = jwtVerifier.parseAccount(command.accessToken)
        jwtVerifier.verifyRefreshToken(account, command.refreshToken)
        val user = findUserPort.findUserByAccount(account) ?: throw UserNotFoundException()
        return jwtTokenProvider.createToken(CreateTokenCommand.toTokenCommand(user))
    }

    @Transactional(readOnly = true)
    override fun logout(command: LogoutUserCommand) {
        val user = findUserPort.findUserById(command.userId) ?: throw UserNotFoundException()
        jwtVerifier.expireRefreshToken(user.account)
    }
}
