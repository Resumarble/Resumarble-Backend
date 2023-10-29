package resumarble.core.global.auth

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoUserInfo(
    private val sub: String,
    private val email: String,
    private val name: String
) : OauthUserInfo {
    override fun sub(): String {
        return sub
    }

    override fun email(): String {
        return email
    }

    override fun name(): String {
        return name
    }
}
