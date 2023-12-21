package resumarble.gateway.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtVerifier {

    @Value("\${external.jwt.secret-key}")
    private lateinit var secretKey: String

    fun verify(token: String): DecodedJWT {
        try {
            return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
        } catch (e: Exception) {
            throw JwtException("토큰이 유효하지 않습니다.")
        }
    }
}
