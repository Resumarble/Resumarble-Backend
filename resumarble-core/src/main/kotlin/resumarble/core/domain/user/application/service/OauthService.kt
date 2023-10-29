package resumarble.core.domain.user.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.global.auth.OauthUserInfo
import resumarble.core.global.jwt.CreateTokenCommand
import resumarble.core.global.jwt.JwtTokenProvider
import resumarble.core.global.jwt.LoginToken

@Service
class OauthService(
    private val findUserPort: FindUserPort,
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional
    fun loginOauthUser(userInfo: OauthUserInfo): LoginToken {
        val user = findUserPort.findUserByAccount(userInfo.email())
            ?: return registerOauthUser(userInfo)
        return jwtTokenProvider.createToken(CreateTokenCommand.toTokenCommand(user))
    }

    private fun registerOauthUser(userInfo: OauthUserInfo): LoginToken {
        val command = userInfo.toCommand()
        val user = userService.join(command)
        return jwtTokenProvider.createToken(CreateTokenCommand.toTokenCommand(user))
    }
}
