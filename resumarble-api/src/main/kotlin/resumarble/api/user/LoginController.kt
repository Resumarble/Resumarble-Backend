package resumarble.api.user

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.user.application.LogoutUserCommand
import resumarble.core.domain.user.application.port.`in`.LoginUserUseCase
import resumarble.core.domain.user.application.port.`in`.LogoutUserUseCase
import resumarble.core.global.jwt.LoginToken

@RestController
@RequestMapping("/users")
class LoginController(
    private val loginUserUseCase: LoginUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) {

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

    @Operation(summary = "액세스 토큰 재발급", description = "리프레시 토큰을 이용하여 액세스 토큰을 재발급한다.")
    @PostMapping("/reissue")
    fun reissue(@RequestBody request: ReissueTokenRequest): Response<Unit> {
        loginUserUseCase.reissueToken(request.toCommand())
        return Response.ok()
    }
}
