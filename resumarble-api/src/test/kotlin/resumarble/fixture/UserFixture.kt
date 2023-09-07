package resumarble.fixture

import resumarble.api.user.JoinUserRequest
import resumarble.api.user.LoginUserRequest
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password

object UserFixture {

    fun joinUserRequest(): JoinUserRequest {
        return JoinUserRequest(
            email = "test@test.com",
            password = "test1234",
            nickname = "test"
        )
    }

    fun loginUserRequest(): LoginUserRequest {
        return LoginUserRequest(
            email = "test@test.com",
            password = "test1234"
        )
    }

    fun user(): User {
        return User(
            userId = 1L,
            email = "test@test.com",
            password = password(),
            nickname = "test"
        )
    }

    private fun password(): Password {
        return Password("test1234")
    }
}
