package resumarble.core.domain.user.domain

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.constraints.UserRole
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.error.UnidentifiedUserException

class User(

    val account: String,

    val password: Password,

    val provider: JwtProvider = JwtProvider.RESUMARBLE,

    val role: UserRole = UserRole.ROLE_USER,

    val userId: Long = 0L
) {

    fun authenticate(password: Password) {
        identify(this.password == password)
    }

    private fun identify(value: Boolean) {
        if (!value) {
            throw UnidentifiedUserException()
        }
    }
}
