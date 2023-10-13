package resumarble.core.global.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

@Component
class JwtTokenProvider(
    private val redisTemplate: StringRedisTemplate
) {

    @Value("\${jwt.secret-key}")
    private lateinit var SECRET_KEY: String

    fun createToken(command: CreateTokenCommand): LoginToken {
        val refreshToken = refreshToken()
        val accessToken = accessToken(command)
        redisTemplate.opsForValue().set(USER_KEY_PREFIX + command.account, refreshToken, 14, TimeUnit.DAYS)
        return LoginToken(TOKEN_PREFIX + accessToken, TOKEN_PREFIX + refreshToken)
    }

    protected fun accessToken(command: CreateTokenCommand): String =
        JWT.create()
            .withSubject(command.account)
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
            .withClaim("id", command.userId)
            .withClaim("auth", command.role.name)
            .withClaim("provider", command.provider.name)
            .sign(Algorithm.HMAC256(SECRET_KEY))

    protected fun refreshToken(): String =
        JWT.create()
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(SECRET_KEY))

    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val HEADER_STRING = "Authorization"
        const val ACCESS_EXPIRATION_TIME = 120 * 60 * 1000L // 2시간
        const val USER_KEY_PREFIX = "user:"

        const val REFRESH_EXPIRATION_TIME = 14 * 60 * 60 * 24 * 1000L // 14일
    }
}
