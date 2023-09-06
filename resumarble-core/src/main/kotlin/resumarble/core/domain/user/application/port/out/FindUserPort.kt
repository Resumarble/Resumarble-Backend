package resumarble.core.domain.user.application.port.out

import resumarble.core.domain.user.domain.User

interface FindUserPort {

    fun findUserByEmail(email: String): User

    fun existsUserByEmail(nickname: String): Boolean
}
