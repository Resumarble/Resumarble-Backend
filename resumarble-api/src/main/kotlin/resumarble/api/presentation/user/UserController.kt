package resumarble.api.presentation.user

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import resumarble.api.response.Response
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase

@RestController
class UserController(
    private val joinUserUseCase: JoinUserUseCase
) {

    @PostMapping("/users/join")
    fun join(@RequestBody joinUserRequest: JoinUserRequest): Response<Unit> {
        joinUserUseCase.join(joinUserRequest.toCommand())
        return Response.ok()
    }
}
