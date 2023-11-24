package resumarble.core.global.auth

import resumarble.core.domain.user.application.JoinUserCommand
import resumarble.core.domain.user.constraints.JwtProvider

interface OauthUserInfo {

    fun sub(): String

    fun email(): String

    fun name(): String

    fun toCommand(provider: JwtProvider = JwtProvider.KAKAO): JoinUserCommand {
        return JoinUserCommand(
            account = email(),
            password = sub(),
            provider = provider
        )
    }
}
