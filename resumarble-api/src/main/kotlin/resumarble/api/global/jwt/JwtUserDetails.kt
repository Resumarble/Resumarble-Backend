package resumarble.api.global.jwt

import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class JwtUserDetails(
    private val id: Long,
    val role: String,
    var isAuthenticated: Boolean
) : UserDetails {

    constructor(decodedJWT: DecodedJWT) : this(
        id = decodedJWT.getClaim("id").asLong(),
        role = decodedJWT.getClaim("auth").asString(),
        isAuthenticated = true
    )

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return if (isAuthenticated) {
            listOf(SimpleGrantedAuthority(role))
        } else {
            listOf(SimpleGrantedAuthority(null))
        }
    }

    val userId: Long
        get() = id

    override fun getPassword() = null

    override fun getUsername() = null

    override fun isAccountNonExpired() = false

    override fun isAccountNonLocked() = false

    override fun isCredentialsNonExpired() = false

    override fun isEnabled() = false
}
