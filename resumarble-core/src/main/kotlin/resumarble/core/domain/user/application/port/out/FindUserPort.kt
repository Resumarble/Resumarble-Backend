package resumarble.core.domain.user.application.port.out

import resumarble.core.domain.user.domain.User

interface FindUserPort {

    fun getUserByUserId(userId: Long): User

    fun getUserByEmail(email: String): User

    fun existsUserByEmail(email: String): Boolean
}
