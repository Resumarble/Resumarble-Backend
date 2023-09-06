package resumarble.fixture

import resumarble.api.presentation.user.JoinUserRequest

object UserFixture {

    fun joinUserRequest(): JoinUserRequest {
        return JoinUserRequest(
            email = "test@test.com",
            password = "test1234",
            nickname = "test"
        )
    }
}
