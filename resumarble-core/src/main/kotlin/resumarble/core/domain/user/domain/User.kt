package resumarble.core.domain.user.domain

import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.constraints.UserRole
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.global.error.UnidentifiedUserException

class User private constructor(

    private val userInformation: UserInformation,

    val password: Password,

    val provider: JwtProvider = JwtProvider.RESUMARBLE,

    val role: UserRole = UserRole.ROLE_USER,

    val userId: Long = 0L
) {
    constructor(
        email: String,
        password: Password,
        nickname: String,
        provider: JwtProvider,
        role: UserRole,
        userId: Long
    ) : this(UserInformation(email, nickname), password, provider, role, userId)

    val email: String
        get() = userInformation.email

    val nickname: String
        get() = userInformation.nickname

    fun authenticate(password: Password) {
        identify(this.password == password)
    }

    private fun identify(value: Boolean) {
        if (!value) {
            throw UnidentifiedUserException()
        }
    }
}
