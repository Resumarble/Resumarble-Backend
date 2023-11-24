package resumarble.api.user

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.response.Response
import resumarble.core.domain.user.application.service.OauthService
import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.global.auth.KakaoOauth2Utils
import resumarble.core.global.auth.KakaoUserInfo
import resumarble.core.global.jwt.LoginToken

@RestController
@RequestMapping("/oauth")
class OauthController(
    private val oauthService: OauthService
) {

    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행합니다.")
    @PostMapping("/kakao")
    fun kakaoLogin(@RequestHeader("Authorization") accessToken: String): Response<LoginToken> {
        val userInfo: KakaoUserInfo = KakaoOauth2Utils.getKakaoUserInfo(accessToken)
        val loginToken: LoginToken = oauthService.loginOauthUser(userInfo, JwtProvider.KAKAO)
        return Response.ok(loginToken)
    }
}
