package resumarble.core.domain.user.application.port.out

import resumarble.core.domain.user.domain.User

interface JoinUserPort {
    fun join(user: User): User
}
