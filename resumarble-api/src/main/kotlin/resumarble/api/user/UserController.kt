package resumarble.api.user

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.user.application.DuplicateAccountCommand
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.domain.user.application.service.MyPageResponse
import resumarble.core.domain.user.application.service.UserFacade

@RestController
@RequestMapping("/users")
class UserController(
    private val joinUserUseCase: JoinUserUseCase,
    private val userFacade: UserFacade
) {

    @Operation(summary = "회원 가입", description = "아이디, 패스워드를 입력받아 회원가입을 수행한다.")
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    fun join(@RequestBody joinUserRequest: JoinUserRequest): Response<Unit> {
        joinUserUseCase.join(joinUserRequest.toCommand())
        return Response.ok()
    }

    @Operation(summary = "아이디 중복 확인", description = "이메일을 입력받아 아이디 중복을 수행한다..")
    @PostMapping("/duplicate-account")
    fun duplicateAccount(
        @RequestBody request: DuplicateAccountRequest
    ): Response<Unit> {
        joinUserUseCase.duplicateAccount(DuplicateAccountCommand.of(request.account))
        return Response.ok()
    }

    @Operation(summary = "마이페이지", description = "마이페이지를 조회한다.")
    @GetMapping("/me")
    fun myPage(@AuthenticationPrincipal user: JwtUserDetails): Response<MyPageResponse> {
        return Response.ok(userFacade.getMyPredictions(user.userId))
    }
}
