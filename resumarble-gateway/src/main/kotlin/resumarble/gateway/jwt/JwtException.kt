package resumarble.gateway.jwt

class JwtException(
    override val message: String
) : RuntimeException(message)
