package resumarble.core.global.auth

import resumarble.core.domain.user.application.JoinUserCommand

interface OauthUserInfo {

    fun sub(): String

    fun email(): String

    fun name(): String

    fun toCommand(): JoinUserCommand {
        return JoinUserCommand(
            account = email(),
            password = sub()
        )
    }
}
