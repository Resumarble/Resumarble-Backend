package resumarble.api.user

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerUserWebPort
import resumarble.core.domain.user.application.DuplicateAccountCommand
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.domain.user.application.service.MyPageResponse
import resumarble.core.domain.user.application.service.UserFacade

@RestController
@RequestMapping("/users")
class UserController(
    private val joinUserUseCase: JoinUserUseCase,
    private val userFacade: UserFacade
) : SwaggerUserWebPort {

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    override fun join(@RequestBody request: JoinUserRequest): Response<Unit> {
        joinUserUseCase.join(request.toCommand())
        return Response.ok()
    }

    @PostMapping("/duplicate-account")
    override fun duplicateAccount(
        @RequestBody request: DuplicateAccountRequest
    ): Response<Unit> {
        joinUserUseCase.duplicateAccount(DuplicateAccountCommand.of(request.account))
        return Response.ok()
    }

    @GetMapping("/me")
    override fun myPage(
        @RequestParam(defaultValue = "0") page: Int,
        @AuthenticationPrincipal user: JwtUserDetails
    ): Response<MyPageResponse> {
        return Response.ok(userFacade.getMyPredictions(user.userId))
    }
}
