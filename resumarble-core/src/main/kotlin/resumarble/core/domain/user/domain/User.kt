package resumarble.core.domain.user.domain

import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.error.UnidentifiedUserException

class User(

    val email: String,

    val password: Password,

    val nickname: String,

    var userId: Long = 0L
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
