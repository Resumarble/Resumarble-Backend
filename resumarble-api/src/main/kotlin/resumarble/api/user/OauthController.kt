package resumarble.api.user

import com.fasterxml.jackson.core.JsonProcessingException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.response.Response
import resumarble.core.domain.user.application.service.OauthService
import resumarble.core.global.auth.KakaoOauth2Utils
import resumarble.core.global.auth.KakaoUserInfo
import resumarble.core.global.jwt.LoginToken

@RestController
@RequestMapping("/oauth")
class OauthController(
    private val oauthService: OauthService
) {

    @PostMapping("/kakao")
    @Throws(JsonProcessingException::class)
    fun kakaoLogin(@RequestHeader("Authorization") accessToken: String): Response<LoginToken> {
        val userInfo: KakaoUserInfo = KakaoOauth2Utils.getKakaoUserInfo(accessToken)
        val loginToken: LoginToken = oauthService.loginOauthUser(userInfo)
        return Response.ok(loginToken)
    }
}
