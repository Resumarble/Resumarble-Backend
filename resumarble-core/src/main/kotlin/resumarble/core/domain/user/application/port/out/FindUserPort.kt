package resumarble.core.domain.user.application.port.out

import resumarble.core.domain.user.domain.User

interface FindUserPort {

    fun findUserById(userId: Long): User?

    fun findUserByAccount(account: String): User?

    fun existsUserByAccount(account: String): Boolean
}
