package resumarble.api.presentation.user

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.response.Response
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase

@RestController
@RequestMapping("/users")
class UserController(
    private val joinUserUseCase: JoinUserUseCase
) {

    @Operation(summary = "회원 가입", description = "이메일, 패스워드, 닉네임을 입력받아 회원가입을 진행한다.")
    @PostMapping("/join")
    fun join(@RequestBody joinUserRequest: JoinUserRequest): Response<Unit> {
        joinUserUseCase.join(joinUserRequest.toCommand())
        return Response.ok()
    }
}
