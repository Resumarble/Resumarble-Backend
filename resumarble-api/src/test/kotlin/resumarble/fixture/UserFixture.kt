package resumarble.fixture

import resumarble.api.user.DuplicateAccountRequest
import resumarble.api.user.JoinUserRequest
import resumarble.api.user.LoginUserRequest
import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password

object UserFixture {

    fun joinUserRequest(): JoinUserRequest {
        return JoinUserRequest(
            account = "test",
            password = "test1234"
        )
    }

    fun loginUserRequest(): LoginUserRequest {
        return LoginUserRequest(
            account = "test",
            password = "test1234"
        )
    }

    fun user(): User {
        return User(
            account = "test",
            password = password(),
            provider = JwtProvider.RESUMARBLE
        )
    }

    private fun password(): Password {
        return Password("test1234")
    }

    fun duplicateAccountRequest(): DuplicateAccountRequest {
        return DuplicateAccountRequest(
            account = "duplicateAccount"
        )
    }
}
