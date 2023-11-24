package resumarble.core.global.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

object KakaoOauth2Utils {

    fun getKakaoUserInfo(accessToken: String): KakaoUserInfo {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        // HTTP 요청 보내기
        val kakaoUserInfoRequest = HttpEntity<MultiValueMap<String, String>>(headers)
        val rt = RestTemplate()
        val response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String::class.java
        )

        // responseBody에 있는 정보를 꺼냄
        val responseBody = response.body
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(responseBody)

        val id = jsonNode["id"].asLong()
        val email = jsonNode["kakao_account"]["email"].asText()

        return KakaoUserInfo(
            sub = id.toString(),
            email = email,
            name = email
        )
    }
}
