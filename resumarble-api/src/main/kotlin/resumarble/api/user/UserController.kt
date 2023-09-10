package resumarble.api.user

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.user.application.port.LogoutUserCommand
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.domain.user.application.port.`in`.LoginUserUseCase
import resumarble.core.domain.user.application.port.`in`.LogoutUserUseCase
import resumarble.core.global.jwt.LoginToken

@RestController
@RequestMapping("/users")
class UserController(
    private val joinUserUseCase: JoinUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) {

    @Operation(summary = "회원 가입", description = "이메일, 패스워드, 닉네임을 입력받아 회원가입을 진행한다.")
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    fun join(@RequestBody joinUserRequest: JoinUserRequest): Response<Unit> {
        joinUserUseCase.join(joinUserRequest.toCommand())
        return Response.ok()
    }

    @Operation(summary = "로그인", description = "이메일, 패스워드를 입력받아 로그인을 진행한다.")
    @PostMapping("/login")
    fun login(@RequestBody loginUserRequest: LoginUserRequest): Response<LoginToken> {
        val token = loginUserUseCase.login(loginUserRequest.toCommand())
        return Response.ok(token)
    }

    @Operation(summary = "로그아웃", description = "헤더의 엑세스 토큰으로 로그아웃을 진행한다.")
    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal user: JwtUserDetails): Response<Unit> {
        logoutUserUseCase.logout(LogoutUserCommand.of(user.userId))
        return Response.ok()
    }
}
