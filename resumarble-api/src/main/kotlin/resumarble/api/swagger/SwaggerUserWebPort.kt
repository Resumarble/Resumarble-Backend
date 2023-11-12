package resumarble.api.swagger

import io.swagger.v3.oas.annotations.Operation
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.user.DuplicateAccountRequest
import resumarble.api.user.JoinUserRequest
import resumarble.core.domain.user.application.service.MyPageResponse

interface SwaggerUserWebPort {

    @Operation(summary = "회원 가입", description = "아이디, 패스워드를 입력받아 회원가입을 수행한다.")
    fun join(request: JoinUserRequest): Response<Unit>

    @Operation(summary = "아이디 중복 확인", description = "이메일을 입력받아 아이디 중복을 수행한다..")
    fun duplicateAccount(
        request: DuplicateAccountRequest
    ): Response<Unit>

    @Operation(summary = "마이페이지", description = "마이페이지를 조회한다.")
    fun myPage(page: Int, user: JwtUserDetails): Response<MyPageResponse>
}
