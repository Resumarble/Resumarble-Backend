package resumarble.core.domain.user.application.port.out

import resumarble.core.domain.user.domain.User

interface FindUserPort {

    fun findUserByUserId(userId: Long): User?

    fun findUserByEmail(email: String): User?

    fun existsUserByEmail(email: String): Boolean
}
