package resumarble.api.global.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val exception = getJwtException(request)
        setResponse(response, exception)
    }

    private fun getJwtException(request: HttpServletRequest): JwtException {
        val exception = request.getAttribute("exception") as? String
        return exception?.let { JwtException.valueOf(it) } ?: JwtException.EMPTY_TOKEN
    }
}
